package com.sigcqal.api.application.ModuloAreaSustantiva.InformeAutoridad;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.InformeAutoridad.Model.EstadoAlertaPlazo;
import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Entity.EstatusExpedienteEntity;
import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Repository.EstatusExpedienteJpaRepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Repository.ExpedienteJPARepository;
import com.sigcqal.api.web.ModuloAreaSustantiva.InformeAutoridad.Dto.PlazoInformeAutoridadResponseDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.InformeAutoridad.Dto.RecepcionInformeRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.InformeAutoridad.Dto.RecepcionInformeResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class InformeAutoridadService {

    private static final int DIAS_HABILES_INFORME = 5;
    private static final String ESTATUS_OFICIO_ENVIADO = "OFICIO ENVIADO";
    private static final String ESTATUS_INFORME_RENDIDO = "INFORME RENDIDO";
    private static final DateTimeFormatter FILE_TS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final ExpedienteJPARepository expedienteRepository;
    private final EstatusExpedienteJpaRepository estatusExpedienteRepository;
    private final CalculadorDiasHabiles calculadorDiasHabiles;
    private final FileUploadPort fileUploadPort;

    @Transactional
    public PlazoInformeAutoridadResponseDTO obtenerPlazo(String folio) {
        ExpedienteEntity expediente = obtenerExpedientePorFolio(folio);
        asegurarPlazoCalculable(expediente);

        LocalDate fechaLimite = obtenerOCalcularFechaLimite(expediente);
        boolean informeRecibido = expediente.getFechaRecepcionInforme() != null || esInformeRendido(expediente);
        boolean vencido = !informeRecibido && LocalDate.now().isAfter(fechaLimite);
        int diasRestantes = informeRecibido ? 0 : calculadorDiasHabiles.diasHabilesRestantes(fechaLimite);
        EstadoAlertaPlazo estadoActual = informeRecibido
                ? EstadoAlertaPlazo.INFORME_RECIBIDO
                : calculadorDiasHabiles.determinarEstado(fechaLimite);

        if (estadoActual != expediente.getEstadoAlertaPlazo()) {
            expediente.setEstadoAlertaPlazo(estadoActual);
            expedienteRepository.save(expediente);
        }

        return PlazoInformeAutoridadResponseDTO.builder()
                .folioExpediente(expediente.getFolioGobierno())
                .fechaInicio(expediente.getFechaEnvioOficio().toLocalDate().toString())
                .fechaLimite(fechaLimite.toString())
                .diasHabilesRestantes(vencido ? 0 : diasRestantes)
                .semaforoEstado(estadoActual.name())
                .vencido(vencido)
                .build();
    }

    @Transactional
    public RecepcionInformeResponseDTO registrarInforme(
            String folio,
            LocalDate fechaRecepcion,
            String numeroOficioRespuesta,
            Integer fojas,
            String observaciones,
            String ipCliente,
            MultipartFile archivoPdf) {

        ExpedienteEntity expediente = obtenerExpedientePorFolio(folio);
        asegurarOficioEnviado(expediente);
        validarRecepcion(fechaRecepcion, numeroOficioRespuesta, fojas, archivoPdf);

        LocalDate fechaLimite = obtenerOCalcularFechaLimite(expediente);
        if (LocalDate.now().isAfter(fechaLimite)) {
            throw new InvalidRequestException("El plazo legal de 5 días hábiles ya venció para este expediente.");
        }

        String nombreArchivo = generarNombreArchivo(folio, numeroOficioRespuesta);
        String rutaArchivo = guardarPdfInforme(archivoPdf, nombreArchivo);

        RecepcionInformeRequestDTO request = RecepcionInformeRequestDTO.builder()
                .expedienteId(expediente.getId())
                .numeroOficioRespuesta(numeroOficioRespuesta.trim())
                .foliosCantidad(fojas)
                .fechaRecepcion(fechaRecepcion.atStartOfDay())
                .nombreArchivoPdf(rutaArchivo)
                .observaciones(observaciones)
                .ipCliente(ipCliente)
                .build();

        expediente.setNumeroOficioRespuesta(request.getNumeroOficioRespuesta());
        expediente.setFojasInforme(request.getFoliosCantidad());
        expediente.setFechaRecepcionInforme(request.getFechaRecepcion());
        expediente.setRutaPdfInforme(request.getNombreArchivoPdf());
        expediente.setEstadoAlertaPlazo(EstadoAlertaPlazo.INFORME_RECIBIDO);
        expediente.setNotificacionVencimientoEnviada(Boolean.FALSE);
        expediente.setEstatusExpediente(resolverEstatus(ESTATUS_INFORME_RENDIDO));

        expedienteRepository.save(expediente);

        log.info(
                "Informe de autoridad registrado. expedienteId={}, folio={}, oficioRespuesta={}, ip={}, observaciones={}",
                expediente.getId(),
                expediente.getFolioGobierno(),
                request.getNumeroOficioRespuesta(),
                request.getIpCliente(),
                request.getObservaciones());

        return RecepcionInformeResponseDTO.builder()
                .idRegistroInforme(expediente.getId())
                .estatusExpediente(expediente.getEstatusExpediente().getNombre())
                .numeroOficioRespuesta(request.getNumeroOficioRespuesta())
                .fechaRegistro(LocalDateTime.now())
                .rutaArchivoPdf(rutaArchivo)
                .mensaje("Informe registrado. Se detiene el conteo del plazo y se habilita la notificación inmediata.")
                .puedeNotificar(Boolean.TRUE)
                .build();
    }

    @Transactional
    public void actualizarAlertasProgramadas() {
        List<ExpedienteEntity> pendientes = expedienteRepository.findAllByFechaEnvioOficioIsNotNullAndFechaRecepcionInformeIsNull();
        for (ExpedienteEntity expediente : pendientes) {
            if (!esOficioEnviado(expediente)) {
                continue;
            }

            LocalDate fechaLimite = obtenerOCalcularFechaLimite(expediente);
            EstadoAlertaPlazo estadoNuevo = calculadorDiasHabiles.determinarEstado(fechaLimite);

            if (estadoNuevo != expediente.getEstadoAlertaPlazo()) {
                log.info("Actualizando estado de alerta para folio {}: {} -> {}",
                        expediente.getFolioGobierno(),
                        expediente.getEstadoAlertaPlazo(),
                        estadoNuevo);
                expediente.setEstadoAlertaPlazo(estadoNuevo);
            }

            if (estadoNuevo == EstadoAlertaPlazo.ROJO
                    && !Boolean.TRUE.equals(expediente.getNotificacionVencimientoEnviada())) {
                expediente.setNotificacionVencimientoEnviada(Boolean.TRUE);
                log.warn("Plazo vencido para expediente {}. Se marca alerta roja.", expediente.getFolioGobierno());
            }
        }
    }

    private ExpedienteEntity obtenerExpedientePorFolio(String folio) {
        return expedienteRepository.findByFolioGobierno(folio)
                .orElseThrow(() -> new InvalidRequestException("Expediente no encontrado para el folio " + folio));
    }

    private void asegurarOficioEnviado(ExpedienteEntity expediente) {
        if (!esOficioEnviado(expediente)) {
            throw new InvalidRequestException("El expediente debe estar en estatus 'OFICIO ENVIADO'.");
        }
        if (expediente.getFechaEnvioOficio() == null) {
            throw new InvalidRequestException("El expediente no tiene registrada la fecha de envío del oficio a la autoridad.");
        }
    }

    private void asegurarPlazoCalculable(ExpedienteEntity expediente) {
        if (!(esOficioEnviado(expediente) || esInformeRendido(expediente))) {
            throw new InvalidRequestException("El expediente debe estar en estatus 'OFICIO ENVIADO' o 'INFORME RENDIDO'.");
        }
        if (expediente.getFechaEnvioOficio() == null) {
            throw new InvalidRequestException("El expediente no tiene registrada la fecha de envío del oficio a la autoridad.");
        }
    }

    private boolean esOficioEnviado(ExpedienteEntity expediente) {
        String nombre = expediente.getEstatusExpediente() != null ? expediente.getEstatusExpediente().getNombre() : null;
        return normalizar(nombre).contains(normalizar(ESTATUS_OFICIO_ENVIADO));
    }

    private boolean esInformeRendido(ExpedienteEntity expediente) {
        String nombre = expediente.getEstatusExpediente() != null ? expediente.getEstatusExpediente().getNombre() : null;
        return normalizar(nombre).contains(normalizar(ESTATUS_INFORME_RENDIDO));
    }

    private void validarRecepcion(
            LocalDate fechaRecepcion,
            String numeroOficioRespuesta,
            Integer fojas,
            MultipartFile archivoPdf) {
        if (fechaRecepcion == null) {
            throw new InvalidRequestException("La fecha de recepción es obligatoria.");
        }
        if (numeroOficioRespuesta == null || numeroOficioRespuesta.trim().isEmpty()) {
            throw new InvalidRequestException("El número de oficio de respuesta es obligatorio.");
        }
        if (fojas == null || fojas <= 0) {
            throw new InvalidRequestException("La cantidad de fojas debe ser mayor a cero.");
        }
        if (archivoPdf == null || archivoPdf.isEmpty()) {
            throw new InvalidRequestException("Debe adjuntar el PDF del informe.");
        }
        String originalFilename = archivoPdf.getOriginalFilename() != null
                ? archivoPdf.getOriginalFilename()
                : "";
        if (!"application/pdf".equalsIgnoreCase(archivoPdf.getContentType())
                && !originalFilename.toLowerCase(Locale.ROOT).endsWith(".pdf")) {
            throw new InvalidRequestException("El archivo adjunto debe ser un PDF.");
        }
    }

    private LocalDate obtenerOCalcularFechaLimite(ExpedienteEntity expediente) {
        if (expediente.getFechaLimiteInforme() == null) {
            LocalDate fechaLimite = calculadorDiasHabiles.calcularFechaLimite(
                    expediente.getFechaEnvioOficio(),
                    DIAS_HABILES_INFORME);
            expediente.setFechaLimiteInforme(fechaLimite);
            if (expediente.getEstadoAlertaPlazo() == null) {
                expediente.setEstadoAlertaPlazo(calculadorDiasHabiles.determinarEstado(fechaLimite));
            }
            if (expediente.getNotificacionVencimientoEnviada() == null) {
                expediente.setNotificacionVencimientoEnviada(Boolean.FALSE);
            }
            expedienteRepository.save(expediente);
        }
        return expediente.getFechaLimiteInforme();
    }

    private EstatusExpedienteEntity resolverEstatus(String nombre) {
        return estatusExpedienteRepository.findByNombreIgnoreCase(nombre)
                .orElseThrow(() -> new InvalidRequestException("No existe el estatus configurado: " + nombre));
    }

    private String guardarPdfInforme(MultipartFile archivoPdf, String nombreArchivo) {
        try {
            return fileUploadPort.guardarArchivoExpediente(archivoPdf.getBytes(), nombreArchivo);
        } catch (Exception ex) {
            throw new InvalidRequestException("No fue posible almacenar el PDF del informe.");
        }
    }

    private String generarNombreArchivo(String folio, String numeroOficioRespuesta) {
        String prefijo = normalizar(numeroOficioRespuesta).replace(' ', '_');
        return "INF_" + folio + "_" + prefijo + "_" + FILE_TS.format(LocalDateTime.now()) + ".pdf";
    }

    private String normalizar(String valor) {
        if (valor == null) {
            return "";
        }
        String base = Normalizer.normalize(valor, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return base.replaceAll("[^A-Za-z0-9]+", " ").trim().toUpperCase(Locale.ROOT);
    }
}
