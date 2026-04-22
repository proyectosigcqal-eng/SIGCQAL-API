package com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.exception.ErrorEnvioNotificacionException;
import com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.exception.FolioSinAreaAsignadaException;
import com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.exception.FolioYaConcluidoException;
import com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.exception.MensajeAlertaInvalidoException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model.AlertaSeguimiento;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model.AreaResponsable;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model.FolioConRetraso;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Port.AlertaSeguimientoRepositoryPort;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Port.AuditoriaPort;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Port.CorrespondenciaQueryPort;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Port.NotificacionPort;

@Service
public class EnviarAlertaSeguimientoService {
    @Autowired
    private AlertaSeguimientoRepositoryPort alertaSeguimientoRepositoryPort;

    @Autowired
    private CorrespondenciaQueryPort correspondenciaQueryPort;

    @Autowired
    private NotificacionPort notificacionPort;

    @Autowired
    private AuditoriaPort auditoriaPort;

    @Transactional(noRollbackFor = ErrorEnvioNotificacionException.class)
    public AlertaSeguimiento enviar(Long idFolio, String mensajeUrgencia, Long idUsuarioEmisor) {
        if (mensajeUrgencia != null && mensajeUrgencia.length() > 500) {
            throw new MensajeAlertaInvalidoException("El mensaje de urgencia no puede exceder 500 caracteres");
        }

        AreaResponsable areaResponsable = correspondenciaQueryPort.obtenerAreaResponsable(idFolio);
        if (areaResponsable == null || areaResponsable.getIdArea() == null) {
            throw new FolioSinAreaAsignadaException("El folio no tiene un área responsable asignada");
        }
        if (areaResponsable.getIdUsuarioResponsable() == null) {
            throw new FolioSinAreaAsignadaException("El folio no tiene usuario responsable asignado");
        }

        String estatusActual = correspondenciaQueryPort.obtenerEstatusActualFolio(idFolio);
        if (estatusActual == null) {
            throw new FolioYaConcluidoException("No fue posible determinar el estatus actual del folio");
        }
        if (!estatusActual.equalsIgnoreCase("TURNADO") && !estatusActual.equalsIgnoreCase("EN GESTIÓN")) {
            throw new FolioYaConcluidoException("El folio no se encuentra en un estatus válido para enviar alerta");
        }

        FolioConRetraso detalle = correspondenciaQueryPort.findDetalleFolioById(idFolio);
        if (detalle == null) {
            throw new ResourceNotFoundException("Folio", idFolio);
        }
        LocalDate hoy = LocalDate.now();
        Long diasAtraso = detalle.getFechaRegistro() == null ? 0L : ChronoUnit.DAYS.between(detalle.getFechaRegistro(), hoy);

        String folioUnico = detalle.getFolioUnico();
        String asunto = "ALERTA: Folio " + folioUnico + " con " + diasAtraso + " días de atraso";

        AlertaSeguimiento alerta = new AlertaSeguimiento();
        alerta.setIdFolio(idFolio);
        alerta.setFolioUnico(folioUnico);
        alerta.setIdUsuarioEmisor(idUsuarioEmisor);
        alerta.setIdAreaDestinataria(areaResponsable.getIdArea());
        alerta.setNombreAreaDestinataria(areaResponsable.getNombreArea());
        alerta.setMensajeUrgencia(mensajeUrgencia);
        alerta.setFechaEnvio(LocalDateTime.now());
        alerta.setDiasAtraso(diasAtraso);

        AlertaSeguimiento persisted = alertaSeguimientoRepositoryPort.save(alerta);

        try {
            notificacionPort.enviarNotificacionAArea(areaResponsable.getIdUsuarioResponsable(), asunto, mensajeUrgencia, idFolio);
        } catch (Exception ex) {
            throw new ErrorEnvioNotificacionException("No fue posible enviar la notificación al área destinataria");
        }

        String observaciones = "Alerta de seguimiento enviada al área " + areaResponsable.getNombreArea() + ". Mensaje: " + (mensajeUrgencia == null ? "" : mensajeUrgencia);
        auditoriaPort.registrarEvento(idFolio, idUsuarioEmisor, estatusActual, estatusActual, observaciones);

        return persisted;
    }
}
