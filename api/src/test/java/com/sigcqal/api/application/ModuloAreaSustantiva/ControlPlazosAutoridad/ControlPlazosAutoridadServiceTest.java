package com.sigcqal.api.application.ModuloAreaSustantiva.ControlPlazosAutoridad;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.PeriodoPrevencion.DiaInhabil.Model.DiaInhabil;
import com.sigcqal.api.domain.ModuloAreaSustantiva.PeriodoPrevencion.DiaInhabil.Port.DiaInhabilRepositoryPort;
import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Entity.EstatusExpedienteEntity;
import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Repository.EstatusExpedienteJpaRepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Repository.ExpedienteJPARepository;
import com.sigcqal.api.web.ModuloAreaSustantiva.ControlPlazosAutoridad.Dto.RegistroInformeAutoridadRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.ControlPlazosAutoridad.Dto.SemaforoEstadoEnum;

@ExtendWith(MockitoExtension.class)
class ControlPlazosAutoridadServiceTest {

    @Mock
    private ExpedienteJPARepository expedienteRepository;

    @Mock
    private DiaInhabilRepositoryPort diaInhabilRepositoryPort;

    @Mock
    private EstatusExpedienteJpaRepository estatusRepository;

    @Mock
    private FileUploadPort fileUploadPort;

    @Mock
    private PlazoAutoridadNotifier notifier;

    @Test
    void diasHabilesConsumidos_ignoraFinSemanaEInhabiles() {
        var service = new ControlPlazosAutoridadService(
                expedienteRepository,
                diaInhabilRepositoryPort,
                estatusRepository,
                fileUploadPort,
                new ControlPlazosAutoridadMapper(),
                notifier);

        LocalDateTime fechaEnvio = LocalDateTime.of(2026, 6, 1, 10, 0);
        LocalDate hoy = LocalDate.of(2026, 6, 8);
        Set<LocalDate> inhabiles = Set.of(LocalDate.of(2026, 6, 4));

        int dias = service.diasHabilesConsumidos(fechaEnvio, inhabiles, hoy);
        assertThat(dias).isEqualTo(4);
    }

    @Test
    void calcularSemaforo_clasificaCorrectamente() {
        var service = new ControlPlazosAutoridadService(
                expedienteRepository,
                diaInhabilRepositoryPort,
                estatusRepository,
                fileUploadPort,
                new ControlPlazosAutoridadMapper(),
                notifier);

        assertThat(service.calcularSemaforo(0)).isEqualTo(SemaforoEstadoEnum.VERDE);
        assertThat(service.calcularSemaforo(3)).isEqualTo(SemaforoEstadoEnum.VERDE);
        assertThat(service.calcularSemaforo(4)).isEqualTo(SemaforoEstadoEnum.AMARILLO);
        assertThat(service.calcularSemaforo(5)).isEqualTo(SemaforoEstadoEnum.ROJO);
    }

    @Test
    void registrarInforme_pdfEsObligatorio() {
        var service = new ControlPlazosAutoridadService(
                expedienteRepository,
                diaInhabilRepositoryPort,
                estatusRepository,
                fileUploadPort,
                new ControlPlazosAutoridadMapper(),
                notifier);

        RegistroInformeAutoridadRequestDTO request = new RegistroInformeAutoridadRequestDTO();
        request.setNumeroOficioRespuesta("OF-1");
        request.setFojas(1);
        request.setFechaRecepcion(LocalDate.of(2026, 6, 12));

        assertThatThrownBy(() -> service.registrarInforme(1L, request, null))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessageContaining("PDF");
    }

    @Test
    void registrarInforme_cambiaEstatusYGuardaCampos() throws Exception {
        var service = new ControlPlazosAutoridadService(
                expedienteRepository,
                diaInhabilRepositoryPort,
                estatusRepository,
                fileUploadPort,
                new ControlPlazosAutoridadMapper(),
                notifier);

        EstatusExpedienteEntity estatusOficioEnviado = new EstatusExpedienteEntity();
        estatusOficioEnviado.setId(10L);
        estatusOficioEnviado.setNombre("OFICIO ENVIADO");

        ExpedienteEntity expediente = new ExpedienteEntity();
        expediente.setId(1);
        expediente.setFolioGobierno("FOL-1");
        expediente.setFechaEnvioOficioAutoridad(LocalDateTime.of(2026, 6, 1, 9, 0));
        expediente.setEstatusExpediente(estatusOficioEnviado);
        expediente.setFechaRecepcionInforme(null);

        when(expedienteRepository.findById(anyInt())).thenReturn(Optional.of(expediente));
        when(diaInhabilRepositoryPort.findByRangoFechas(any(), any())).thenReturn(List.of());

        EstatusExpedienteEntity estatusInformeRendido = new EstatusExpedienteEntity();
        estatusInformeRendido.setId(11L);
        estatusInformeRendido.setNombre("INFORME RENDIDO");
        when(estatusRepository.findByNombreIgnoreCase(anyString())).thenReturn(Optional.of(estatusInformeRendido));

        when(fileUploadPort.guardarArchivoExpediente(any(), anyString())).thenReturn("/api/files/expedientes/a.pdf");
        when(expedienteRepository.save(any())).thenAnswer((inv) -> inv.getArgument(0));

        RegistroInformeAutoridadRequestDTO request = new RegistroInformeAutoridadRequestDTO();
        request.setNumeroOficioRespuesta("OF-RESP/1");
        request.setFojas(10);
        request.setFechaRecepcion(LocalDate.of(2026, 6, 12));

        MockMultipartFile pdf = new MockMultipartFile("pdf", "doc.pdf", "application/pdf", "x".getBytes());

        var resp = service.registrarInforme(1L, request, pdf);

        assertThat(resp.getExpedienteId()).isEqualTo(1L);
        assertThat(expediente.getNumeroOficioRespuesta()).isEqualTo("OF-RESP/1");
        assertThat(expediente.getFojasInforme()).isEqualTo(10);
        assertThat(expediente.getFechaRecepcionInforme()).isEqualTo(LocalDate.of(2026, 6, 12).atStartOfDay());
        assertThat(expediente.getRutaPdfInforme()).isEqualTo("/api/files/expedientes/a.pdf");
        assertThat(expediente.getEstatusExpediente().getNombre()).isEqualTo("INFORME RENDIDO");
    }

    @Test
    void recalcularSemaforosVencidos_notificaUnaVezEnRojo() {
        var service = new ControlPlazosAutoridadService(
                expedienteRepository,
                diaInhabilRepositoryPort,
                estatusRepository,
                fileUploadPort,
                new ControlPlazosAutoridadMapper(),
                notifier);

        ExpedienteEntity expediente = new ExpedienteEntity();
        expediente.setId(1);
        expediente.setFolioGobierno("FOL-1");
        expediente.setFechaEnvioOficioAutoridad(LocalDateTime.now().minusDays(10));
        expediente.setFechaRecepcionInforme(null);
        expediente.setNotificacionVencimientoEnviada(Boolean.FALSE);

        when(expedienteRepository.findByFechaEnvioOficioAutoridadIsNotNullAndFechaRecepcionInformeIsNull())
                .thenReturn(List.of(expediente));
        when(diaInhabilRepositoryPort.findByRangoFechas(any(), any())).thenReturn(List.of());
        when(expedienteRepository.save(any())).thenAnswer((inv) -> inv.getArgument(0));

        service.recalcularSemaforosVencidos();

        assertThat(expediente.getEstadoAlerta5Dias()).isEqualTo("ROJO");
        assertThat(expediente.getNotificacionVencimientoEnviada()).isTrue();
        verify(notifier, times(1)).notificarVencimiento(1L, "FOL-1");
    }
}

