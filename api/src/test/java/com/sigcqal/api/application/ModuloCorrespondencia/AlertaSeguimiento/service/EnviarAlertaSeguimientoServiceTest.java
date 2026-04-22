package com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.exception.ErrorEnvioNotificacionException;
import com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.exception.FolioSinAreaAsignadaException;
import com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.exception.FolioYaConcluidoException;
import com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.exception.MensajeAlertaInvalidoException;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model.AlertaSeguimiento;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model.AreaResponsable;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model.FolioConRetraso;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Port.AlertaSeguimientoRepositoryPort;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Port.AuditoriaPort;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Port.CorrespondenciaQueryPort;
import com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Port.NotificacionPort;

@ExtendWith(MockitoExtension.class)
class EnviarAlertaSeguimientoServiceTest {
    @Mock
    private AlertaSeguimientoRepositoryPort alertaSeguimientoRepositoryPort;

    @Mock
    private CorrespondenciaQueryPort correspondenciaQueryPort;

    @Mock
    private NotificacionPort notificacionPort;

    @Mock
    private AuditoriaPort auditoriaPort;

    @InjectMocks
    private EnviarAlertaSeguimientoService service;

    @Test
    void casoFeliz_alertaEnviadaCorrectamente() {
        Long idFolio = 10L;
        Long idUsuario = 99L;

        when(correspondenciaQueryPort.obtenerAreaResponsable(idFolio))
                .thenReturn(new AreaResponsable(7L, "Area X", 100L));
        when(correspondenciaQueryPort.obtenerEstatusActualFolio(idFolio)).thenReturn("Asignado");

        FolioConRetraso detalle = new FolioConRetraso();
        detalle.setIdFolio(idFolio);
        detalle.setFolioUnico("FOL-001");
        detalle.setFechaRegistro(LocalDate.now().minusDays(5));
        when(correspondenciaQueryPort.findDetalleFolioById(idFolio)).thenReturn(detalle);

        when(alertaSeguimientoRepositoryPort.save(any())).thenAnswer(inv -> {
            AlertaSeguimiento a = inv.getArgument(0);
            a.setIdAlerta(1L);
            a.setNombreAreaDestinataria("Area X");
            a.setFolioUnico("FOL-001");
            return a;
        });

        AlertaSeguimiento result = service.enviar(idFolio, "Mensaje", idUsuario);

        assertNotNull(result);
        assertEquals(1L, result.getIdAlerta());

        ArgumentCaptor<AlertaSeguimiento> captor = ArgumentCaptor.forClass(AlertaSeguimiento.class);
        verify(alertaSeguimientoRepositoryPort, times(1)).save(captor.capture());
        AlertaSeguimiento saved = captor.getValue();
        assertEquals(idFolio, saved.getIdFolio());
        assertEquals(idUsuario, saved.getIdUsuarioEmisor());
        assertEquals(7L, saved.getIdAreaDestinataria());
        assertEquals("Mensaje", saved.getMensajeUrgencia());
        assertEquals(5L, saved.getDiasAtraso());

        verify(notificacionPort, times(1)).enviarNotificacionAArea(anyLong(), any(), any(), anyLong());
        verify(auditoriaPort, times(1)).registrarEvento(anyLong(), anyLong(), any(), any(), any());
    }

    @Test
    void fe01_folioSinAreaAsignada() {
        when(correspondenciaQueryPort.obtenerAreaResponsable(10L)).thenReturn(null);
        assertThrows(FolioSinAreaAsignadaException.class, () -> service.enviar(10L, "ok", 1L));
        verify(alertaSeguimientoRepositoryPort, never()).save(any());
    }

    @Test
    void fe02_folioConcluido() {
        when(correspondenciaQueryPort.obtenerAreaResponsable(10L)).thenReturn(new AreaResponsable(7L, "Area X", 100L));
        when(correspondenciaQueryPort.obtenerEstatusActualFolio(10L)).thenReturn("Concluido");
        assertThrows(FolioYaConcluidoException.class, () -> service.enviar(10L, "ok", 1L));
        verify(alertaSeguimientoRepositoryPort, never()).save(any());
    }

    @Test
    void fe03_fallaNotificacion_peroAlertaPersiste() {
        Long idFolio = 10L;
        when(correspondenciaQueryPort.obtenerAreaResponsable(idFolio)).thenReturn(new AreaResponsable(7L, "Area X", 100L));
        when(correspondenciaQueryPort.obtenerEstatusActualFolio(idFolio)).thenReturn("En Seguimiento");

        FolioConRetraso detalle = new FolioConRetraso();
        detalle.setIdFolio(idFolio);
        detalle.setFolioUnico("FOL-001");
        detalle.setFechaRegistro(LocalDate.now().minusDays(2));
        when(correspondenciaQueryPort.findDetalleFolioById(idFolio)).thenReturn(detalle);

        when(alertaSeguimientoRepositoryPort.save(any())).thenAnswer(inv -> {
            AlertaSeguimiento a = inv.getArgument(0);
            a.setIdAlerta(1L);
            return a;
        });

        doThrow(new RuntimeException("fallo"))
                .when(notificacionPort)
                .enviarNotificacionAArea(anyLong(), any(), any(), anyLong());

        assertThrows(ErrorEnvioNotificacionException.class, () -> service.enviar(idFolio, "ok", 1L));
        verify(alertaSeguimientoRepositoryPort, times(1)).save(any());
        verify(auditoriaPort, never()).registrarEvento(anyLong(), anyLong(), any(), any(), any());
    }

    @Test
    void mensajeMayor500_invalido() {
        String mensaje = "a".repeat(501);
        assertThrows(MensajeAlertaInvalidoException.class, () -> service.enviar(10L, mensaje, 1L));
    }
}
