package com.sigcqal.api.application.ModuloAreaSustantiva.ControlPlazosAutoridad;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.PeriodoPrevencion.DiaInhabil.Port.DiaInhabilRepositoryPort;
import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Entity.EstatusExpedienteEntity;
import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Repository.EstatusExpedienteJpaRepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Repository.ExpedienteJPARepository;
import com.sigcqal.api.web.ModuloAreaSustantiva.ControlPlazosAutoridad.Dto.RegistroInformeAutoridadRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.ControlPlazosAutoridad.Dto.RegistroInformeAutoridadResponseDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.ControlPlazosAutoridad.Dto.SemaforoAutoridadResponseDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.ControlPlazosAutoridad.Dto.SemaforoEstadoEnum;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ControlPlazosAutoridadService {

    private static final int PLAZO_DIAS_HABILES = 5;
    private static final int RANGO_DIAS_INHABILES = 60;
    private static final String ESTATUS_OFICIO_ENVIADO = "OFICIO ENVIADO";
    private static final String ESTATUS_INFORME_RENDIDO = "INFORME RENDIDO";
    private static final String ESTADO_ATENDIDO = "ATENDIDO";

    private final ExpedienteJPARepository expedienteRepository;
    private final DiaInhabilRepositoryPort diaInhabilRepositoryPort;
    private final EstatusExpedienteJpaRepository estatusRepository;
    private final FileUploadPort fileUploadPort;
    private final ControlPlazosAutoridadMapper mapper;
    private final PlazoAutoridadNotifier notifier;

    public SemaforoAutoridadResponseDTO obtenerSemaforo(Long expedienteId) {
        ExpedienteEntity expediente = obtenerExpediente(expedienteId);

        if (expediente.getFechaEnvioOficioAutoridad() == null || expediente.getFechaRecepcionInforme() != null) {
            return null;
        }

        LocalDate hoy = LocalDate.now();
        Set<LocalDate> diasInhabiles = obtenerDiasInhabiles(
                expediente.getFechaEnvioOficioAutoridad().toLocalDate(),
                expediente.getFechaEnvioOficioAutoridad().toLocalDate().plusDays(RANGO_DIAS_INHABILES));

        int diasConsumidos = diasHabilesConsumidos(expediente.getFechaEnvioOficioAutoridad(), diasInhabiles, hoy);

        LocalDate fechaLimite = calcularFechaLimite(expediente.getFechaEnvioOficioAutoridad(), diasInhabiles);

        return mapper.toSemaforoResponse(
                expediente,
                calcularSemaforo(diasConsumidos),
                Math.max(0, PLAZO_DIAS_HABILES - diasConsumidos),
                fechaLimite,
                diasConsumidos >= PLAZO_DIAS_HABILES);
    }

    @Transactional
    public RegistroInformeAutoridadResponseDTO registrarInforme(
            Long expedienteId,
            RegistroInformeAutoridadRequestDTO request,
            MultipartFile pdf) throws IOException {

        validarRegistro(request, pdf);

        ExpedienteEntity expediente = obtenerExpediente(expedienteId);

        if (expediente.getFechaEnvioOficioAutoridad() == null) {
            throw new InvalidRequestException("El expediente no tiene fecha de envío de oficio de autoridad.");
        }

        validarEstatusOficioEnviado(expediente);

        if (expediente.getFechaRecepcionInforme() != null) {
            throw new InvalidRequestException("El informe de autoridad ya fue registrado para este expediente.");
        }

        Set<LocalDate> diasInhabiles = obtenerDiasInhabiles(
                expediente.getFechaEnvioOficioAutoridad().toLocalDate(),
                expediente.getFechaEnvioOficioAutoridad().toLocalDate().plusDays(RANGO_DIAS_INHABILES));

        LocalDate fechaLimite = calcularFechaLimite(expediente.getFechaEnvioOficioAutoridad(), diasInhabiles);

        String nombreArchivo = construirNombreArchivo(expedienteId, request.getNumeroOficioRespuesta());

        String rutaPdf = fileUploadPort.guardarArchivoExpediente(pdf.getBytes(), nombreArchivo);

        expediente.setNumeroOficioRespuesta(request.getNumeroOficioRespuesta().trim());
        expediente.setFojasInforme(request.getFojas());
        expediente.setFechaRecepcionInforme(request.getFechaRecepcion().atStartOfDay());
        expediente.setRutaPdfInforme(rutaPdf);
        expediente.setFechaLimiteInforme(fechaLimite);
        expediente.setEstadoAlerta5Dias(ESTADO_ATENDIDO);
        expediente.setNotificacionVencimientoEnviada(Boolean.FALSE);
        expediente.setEstatusExpediente(obtenerEstatusInformeRendido());

        ExpedienteEntity guardado = expedienteRepository.save(expediente);
        return mapper.toRegistroResponse(guardado);
    }

    @Transactional
    public void recalcularSemaforosVencidos() {
        for (ExpedienteEntity expediente : expedienteRepository
                .findByFechaEnvioOficioAutoridadIsNotNullAndFechaRecepcionInformeIsNull()) {

            Set<LocalDate> diasInhabiles = obtenerDiasInhabiles(
                    expediente.getFechaEnvioOficioAutoridad().toLocalDate(),
                    expediente.getFechaEnvioOficioAutoridad().toLocalDate().plusDays(RANGO_DIAS_INHABILES));

            int diasConsumidos = diasHabilesConsumidos(
                    expediente.getFechaEnvioOficioAutoridad(),
                    diasInhabiles,
                    LocalDate.now());

            SemaforoEstadoEnum semaforo = calcularSemaforo(diasConsumidos);
            LocalDate fechaLimite = calcularFechaLimite(expediente.getFechaEnvioOficioAutoridad(), diasInhabiles);

            expediente.setFechaLimiteInforme(fechaLimite);
            expediente.setEstadoAlerta5Dias(semaforo.name());

            if (semaforo == SemaforoEstadoEnum.ROJO && !Boolean.TRUE.equals(expediente.getNotificacionVencimientoEnviada())) {
                notifier.notificarVencimiento(expediente.getId().longValue(), expediente.getFolioGobierno());
                expediente.setNotificacionVencimientoEnviada(Boolean.TRUE);
            }

            expedienteRepository.save(expediente);
        }
    }

    int diasHabilesConsumidos(LocalDateTime fechaEnvio, Set<LocalDate> diasInhabiles, LocalDate hoy) {
        int dias = 0;
        LocalDate iterador = fechaEnvio.toLocalDate().plusDays(1);

        while (!iterador.isAfter(hoy)) {
            if (esDiaHabil(iterador, diasInhabiles)) {
                dias++;
            }
            iterador = iterador.plusDays(1);
        }

        return dias;
    }

    SemaforoEstadoEnum calcularSemaforo(int diasConsumidos) {
        if (diasConsumidos >= PLAZO_DIAS_HABILES) {
            return SemaforoEstadoEnum.ROJO;
        }
        if (diasConsumidos == PLAZO_DIAS_HABILES - 1) {
            return SemaforoEstadoEnum.AMARILLO;
        }
        return SemaforoEstadoEnum.VERDE;
    }

    private Set<LocalDate> obtenerDiasInhabiles(LocalDate desde, LocalDate hasta) {
        return diaInhabilRepositoryPort.findByRangoFechas(desde, hasta)
                .stream()
                .map((dia) -> dia.getFecha())
                .collect(Collectors.toSet());
    }

    private LocalDate calcularFechaLimite(LocalDateTime fechaEnvio, Set<LocalDate> diasInhabiles) {
        LocalDate fecha = fechaEnvio.toLocalDate();
        int contados = 0;

        while (contados < PLAZO_DIAS_HABILES) {
            fecha = fecha.plusDays(1);
            if (esDiaHabil(fecha, diasInhabiles)) {
                contados++;
            }
        }

        return fecha;
    }

    private boolean esDiaHabil(LocalDate fecha, Set<LocalDate> diasInhabiles) {
        return fecha.getDayOfWeek() != DayOfWeek.SATURDAY
                && fecha.getDayOfWeek() != DayOfWeek.SUNDAY
                && !diasInhabiles.contains(fecha);
    }

    private ExpedienteEntity obtenerExpediente(Long expedienteId) {
        Integer id = validarIdExpediente(expedienteId);
        return expedienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expediente", expedienteId));
    }

    private Integer validarIdExpediente(Long expedienteId) {
        if (expedienteId == null || expedienteId <= 0 || expedienteId > Integer.MAX_VALUE) {
            throw new InvalidRequestException("El expedienteId debe ser un entero positivo válido.");
        }
        return expedienteId.intValue();
    }

    private void validarRegistro(RegistroInformeAutoridadRequestDTO request, MultipartFile pdf) {
        if (request == null) {
            throw new InvalidRequestException("La parte 'request' es obligatoria.");
        }
        if (request.getNumeroOficioRespuesta() == null || request.getNumeroOficioRespuesta().trim().isEmpty()) {
            throw new InvalidRequestException("El número de oficio de respuesta es obligatorio.");
        }
        if (request.getFojas() == null || request.getFojas() <= 0) {
            throw new InvalidRequestException("Las fojas deben ser mayores a 0.");
        }
        if (request.getFechaRecepcion() == null) {
            throw new InvalidRequestException("La fecha de recepción es obligatoria.");
        }
        if (pdf == null || pdf.isEmpty()) {
            throw new InvalidRequestException("El PDF firmado es obligatorio.");
        }
    }

    private void validarEstatusOficioEnviado(ExpedienteEntity expediente) {
        if (expediente.getEstatusExpediente() == null
                || expediente.getEstatusExpediente().getNombre() == null
                || !ESTATUS_OFICIO_ENVIADO.equalsIgnoreCase(expediente.getEstatusExpediente().getNombre().trim())) {
            throw new InvalidRequestException("El expediente debe estar en estatus OFICIO ENVIADO para registrar el informe.");
        }
    }

    private EstatusExpedienteEntity obtenerEstatusInformeRendido() {
        return estatusRepository.findByNombreIgnoreCase(ESTATUS_INFORME_RENDIDO)
                .orElseThrow(() -> new InvalidRequestException("No existe el estatus de catálogo INFORME RENDIDO."));
    }

    private String construirNombreArchivo(Long expedienteId, String numeroOficioRespuesta) {
        String oficioNormalizado = numeroOficioRespuesta.trim()
                .replaceAll("[\\\\/:*?\"<>|\\s]+", "_")
                .replaceAll("_+", "_");
        return "EXP_" + expedienteId + "_INF_AUTORIDAD_" + oficioNormalizado + ".pdf";
    }
}

