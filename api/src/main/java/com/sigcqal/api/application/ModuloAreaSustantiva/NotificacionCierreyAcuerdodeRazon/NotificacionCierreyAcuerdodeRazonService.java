package com.sigcqal.api.application.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon;

import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Repository.QuejaJPARepository;

import lombok.RequiredArgsConstructor;

import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Repository.ExpedienteJPARepository; // ajusta el paquete si difiere

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigcqal.api.domain.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Model.NotificacionCierreyAcuerdodeRazon;
import com.sigcqal.api.domain.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Port.NotificacionCierreyAcuerdodeRazonRepositoryPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Queja.Model.EstatusQuejaIds;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificacionCierreyAcuerdodeRazonService {

    private final NotificacionCierreyAcuerdodeRazonRepositoryPort cierreRepository;
    private final QuejaJPARepository quejaJpaRepository;           // ← nuevo
    private final ExpedienteJPARepository expedienteJpaRepository; // ← nuevo

    @Transactional
    public NotificacionCierreyAcuerdodeRazon registrarCierreDefinitivo(NotificacionCierreyAcuerdodeRazon cierre) {

        List<NotificacionCierreyAcuerdodeRazon> cierresPrevios = cierreRepository.findById(cierre.getIdExpediente());
        if (!cierresPrevios.isEmpty()) {
            throw new RuntimeException("El expediente con ID " + cierre.getIdExpediente() + " ya cuenta con un cierre definitivo registrado.");
        }

        if (cierre.getFechaCierre() == null) {
            cierre.setFechaCierre(LocalDateTime.now());
        }

        NotificacionCierreyAcuerdodeRazon guardado = cierreRepository.save(cierre);

        // ✅ nuevo: avanza estatus y bloquea el expediente en la misma transacción
        quejaJpaRepository.actualizarEstatusQueja(cierre.getIdExpediente(), EstatusQuejaIds.CERRADA);
        expedienteJpaRepository.marcarBloqueado(cierre.getIdExpediente());

        return guardado;
    }
}