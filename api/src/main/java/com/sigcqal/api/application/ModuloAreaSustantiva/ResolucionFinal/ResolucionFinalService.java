package com.sigcqal.api.application.ModuloAreaSustantiva.ResolucionFinal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Optional;

import com.sigcqal.api.application.ModuloCorrespondencia.Documento.GeneradorDocumentoService;
import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.ContestacionAutoridad.Model.ContestacionAutoridad;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Queja.Model.EstatusQuejaIds;
import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Model.ResolucionFinal;
import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Port.ResolucionFinalRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ContestacionAutoridad.Repository.ContestacionAutoridadJpaRepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Entity.QuejaEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Repository.QuejaJPARepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Mapper.ResolucionFinalMapper;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Repository.ResolucionFinalJPARepository;
import com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Dto.ResolucionFinalDatosPreviosDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Dto.ResolucionFinalRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Dto.ResolucionFinalResponseDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ResolucionFinalService {

    private final ResolucionFinalRepositoryPort port;
    private final ResolucionFinalMapper          mapper;
    private final FileUploadPort                 fileUploadPort;
    private final GeneradorDocumentoService      generadorDocumentoService;
    private final QuejaJPARepository quejaJpaRepository; // ← nuevo
    private final ResolucionFinalJPARepository resolucionFinalJPARepository; // ← agregar
    private final ContestacionAutoridadJpaRepository respuestaRepo;
    @PersistenceContext
private EntityManager entityManager;

    private static final String NOMBRE_PLANTILLA = "plantilla_resolucion_final.docx";

    // -----------------------------------------------------------------------
    // GUARDAR
    // -----------------------------------------------------------------------
@Transactional
public ResolucionFinalResponseDTO guardar(ResolucionFinalRequestDTO request) {

    validarUnicaResolucionPorExpediente(request.getIdExpediente());

    Integer idAri                     = request.getIdAri();
    Integer idQuejaRespuestaAutoridad  = request.getIdQuejaRespuestaAutoridad();
    Integer idEstatusQueja             = request.getIdEstatusQueja();
    Integer idEstatusExpediente        = request.getIdEstatusExpediente();

    // Intento 1 — resuelve desde BD con los datos previos
    if (idAri == null || idQuejaRespuestaAutoridad == null) {
        List<Object[]> previos = resolucionFinalJPARepository
            .findDatosPreviosByFolioExpediente(request.getIdExpediente());

        if (previos != null && !previos.isEmpty()) {
            Object[] row = previos.get(0);
            if (idAri == null && row[1] != null)
                idAri = ((Number) row[1]).intValue();
            if (idQuejaRespuestaAutoridad == null && row[2] != null)
                idQuejaRespuestaAutoridad = ((Number) row[2]).intValue();
            if (idEstatusQueja == null && row[3] != null)
                idEstatusQueja = ((Number) row[3]).intValue();
            if (idEstatusExpediente == null && row[4] != null)
                idEstatusExpediente = ((Number) row[4]).intValue();
        }
    }

    // Intento 2 — busca contestación existente por expediente
    if (idQuejaRespuestaAutoridad == null) {
        idQuejaRespuestaAutoridad = quejaJpaRepository
            .findPrimerIdRespuestaAutoridad(request.getIdExpediente())
            .orElse(null);
    }

    // Intento 3 — crea placeholder automático para satisfacer el NOT NULL
    if (idQuejaRespuestaAutoridad == null) {
        idQuejaRespuestaAutoridad = crearContestacionPlaceholder(
            request.getIdExpediente());
    }

    ResolucionFinal resolucionFinal = ResolucionFinal.builder()
        .fechaEmisionResolucion(request.getFechaEmisionResolucion())
        .conceptoCobro(request.getConceptoCobro())
        .contactoVia(request.getContactoVia())
        .numeroCredito(request.getNumeroCredito())
        .folioCredito(request.getFolioCredito())
        .idExpediente(request.getIdExpediente())
        .idAri(idAri)
        .idQuejaRespuestaAutoridad(idQuejaRespuestaAutoridad)
        .idEstatusQueja(idEstatusQueja)
        .idEstatusExpediente(idEstatusExpediente)
        .build();

    ResolucionFinal guardado = port.save(resolucionFinal);
    return mapper.toResponse(guardado);
}
    // -----------------------------------------------------------------------
    // BUSCAR POR ID
    // -----------------------------------------------------------------------
    public ResolucionFinalResponseDTO buscarPorId(Integer idResolucionFinal) {
        return port.findById(idResolucionFinal)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException(
                        "Resolución final no encontrada: " + idResolucionFinal));
    }

    // -----------------------------------------------------------------------
    // LISTAR TODOS
    // -----------------------------------------------------------------------
    public List<ResolucionFinalResponseDTO> listarTodos() {
        return port.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    // -----------------------------------------------------------------------
    // LISTAR POR EXPEDIENTE
    // -----------------------------------------------------------------------
    public List<ResolucionFinalResponseDTO> listarPorExpediente(Integer idExpediente) {
        return port.findByIdExpediente(idExpediente)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    // -----------------------------------------------------------------------
    // GENERAR OFICIO (.docx) — ACUERDO DE CIERRE
    // -----------------------------------------------------------------------
    /**
     * Genera el Acuerdo de Cierre (.docx), lo guarda en disco vía
     * FileUploadPort, y actualiza resolucion_final con ruta_resolucion_final
     * y fecha_emision.
     *
     * Los parámetros corresponden EXACTAMENTE a los campos capturados en
     * EditorResolucionFinal.jsx: folio, expedienteNum, autoridadFiscal,
     * fechaSolicitud, nombreContribuyente, motivoQueja, oficioNumero,
     * fechaOficio, fechaIngresoOficio, numeroCreditoMulta, contactoVia,
     * iniciales.
     */

    @Transactional
    public ResolucionFinalResponseDTO generarOficio(
            Integer idResolucionFinal,
            String  folio,
            String  expedienteNum,
            String  autoridadFiscal,
            String  fechaSolicitud,
            String  nombreContribuyente,
            String  motivoQueja,
            String  oficioNumero,
            String  fechaOficio,
            String  fechaIngresoOficio,
            String  numeroCreditoMulta,
            String  contactoVia,
            String  iniciales) {

        ResolucionFinal resolucionExistente = port.findById(idResolucionFinal)
        .orElseThrow(() -> new RuntimeException(
                "Resolución final no encontrada: " + idResolucionFinal));

        try {
            Map<String, String> variables = Map.ofEntries(
                Map.entry("{{FOLIO}}",               nvl(folio,               "[FOLIO]")),
                Map.entry("{{EXPEDIENTE}}",          nvl(expedienteNum,       "[EXPEDIENTE]")),
                Map.entry("{{AUTORIDAD_FISCAL}}",    nvl(autoridadFiscal,     "[AUTORIDAD FISCAL]")),
                Map.entry("{{FECHA}}",               generadorDocumentoService.fechaActual()),
                Map.entry("{{FECHA_SOLICITUD}}",     nvl(fechaSolicitud,      "[FECHA SOLICITUD]")),
                Map.entry("{{CONTRIBUYENTE}}",       nvl(nombreContribuyente, "[CONTRIBUYENTE]")),
                Map.entry("{{MOTIVO_QUEJA}}",        nvl(motivoQueja,         "[MOTIVO DE QUEJA]")),
                Map.entry("{{OFICIO}}",               nvl(oficioNumero,        "[OFICIO]")),
                Map.entry("{{FECHA_OFICIO}}",        nvl(fechaOficio,         "[FECHA OFICIO]")),
                Map.entry("{{FECHA_INGRESO}}",       nvl(fechaIngresoOficio,  "[FECHA INGRESO]")),
                Map.entry("{{NUMERO_CREDITO}}",      nvl(numeroCreditoMulta,  "[NUMERO CREDITO]")),
                Map.entry("{{CONTACTO_VIA}}",        nvl(contactoVia,         "[CONTACTO VÍA]")),
                Map.entry("{{INICIALES}}",           nvl(iniciales,           ""))
            );

            byte[] bytes = generadorDocumentoService.generarDesPlantilla(NOMBRE_PLANTILLA, variables);

            String nombreArchivo = "ACUERDO_CIERRE_" + idResolucionFinal
                    + "_" + System.currentTimeMillis() + ".docx";
            String ruta = fileUploadPort.guardarArchivoExpediente(bytes, nombreArchivo);

            ResolucionFinal actualizado = port.actualizarOficioGenerado(
                    idResolucionFinal, ruta, LocalDateTime.now());

                    if (resolucionExistente.getIdExpediente() != null) {
    quejaJpaRepository.actualizarEstatusQueja(
        resolucionExistente.getIdExpediente(), EstatusQuejaIds.RESOLUCION);
}


            return mapper.toResponse(actualizado);

        } catch (Exception e) {
            log.error("[ResolucionFinal] Error generando el Acuerdo de Cierre para id {}: {}",
                    idResolucionFinal, e.getMessage());
            throw new RuntimeException("Error al generar el Acuerdo de Cierre: " + e.getMessage(), e);
        }
    }

    // -----------------------------------------------------------------------
    // HELPERS PRIVADOS
    // -----------------------------------------------------------------------
    private void validarUnicaResolucionPorExpediente(Integer idExpediente) {
        if (port.existsByIdExpediente(idExpediente)) {
            throw new InvalidRequestException(
                    "El expediente " + idExpediente + " ya cuenta con una resolución final registrada.");
        }
    }

    public ResolucionFinalDatosPreviosDTO obtenerDatosPrevios(String folio) {
    return port.obtenerDatosPreviosPorFolio(folio)
            .orElseThrow(() -> new RuntimeException(
                    "No se encontró información previa para el folio: " + folio));
}

    private String nvl(String v, String fallback) {
        return (v != null && !v.isBlank()) ? v : fallback;
    }

    public record ArchivoDescarga(String nombreArchivo, byte[] contenido) {}

public Optional<ArchivoDescarga> obtenerArchivoPorFolio(String folio) {
    Integer idExpediente = quejaJpaRepository.findIdExpedienteByFolio(folio).orElse(null);
    if (idExpediente == null) return Optional.empty();

    List<ResolucionFinal> lista = port.findByIdExpediente(idExpediente);
    if (lista == null || lista.isEmpty()) return Optional.empty();

    ResolucionFinal ultimo = lista.stream()
        .max(Comparator.comparing(ResolucionFinal::getIdResolucionFinal))
        .orElse(null);
    if (ultimo == null || ultimo.getRutaResolucionFinal() == null || ultimo.getRutaResolucionFinal().isBlank()) {
        return Optional.empty();
    }

    String nombreArchivo = extraerNombreArchivo(ultimo.getRutaResolucionFinal());
    try {
        Path filePath = Paths.get("uploads/expedientes/").resolve(nombreArchivo);
        byte[] contenido = Files.exists(filePath) ? Files.readAllBytes(filePath) : new byte[0];
        return Optional.of(new ArchivoDescarga(nombreArchivo, contenido));
    } catch (IOException e) {
        return Optional.empty();
    }
}

private String extraerNombreArchivo(String url) {
    if (url == null || url.isEmpty()) return "";
    return url.substring(url.lastIndexOf('/') + 1);
}

 @Transactional
private Integer crearContestacionPlaceholder(Integer idExpediente) {

    QuejaEntity queja = quejaJpaRepository
            .findByExpediente_Id(idExpediente)
            .orElseThrow(() -> new InvalidRequestException(
                    "No se encontró queja para el expediente: " + idExpediente));

    String folio = queja.getExpediente() != null
            ? queja.getExpediente().getFolioGobierno()
            : null;

    // ← Usa EntityManager con query nativa para poder usar RETURNING
    Long idNuevo = (Long) entityManager.createNativeQuery("""
            INSERT INTO sustantiva.quejas_respuestas_autoridad
                (id_queja, folio_expediente, observaciones, fecha_registro)
            VALUES
                (:idQueja, :folioExpediente,
                 'Registro automático pendiente de completar', NOW())
            RETURNING id_respuesta_autoridad
            """)
            .setParameter("idQueja",         queja.getIdQueja())
            .setParameter("folioExpediente", folio)
            .getSingleResult();

    log.info("[ResolucionFinal] Placeholder contestación creado: id={} expediente={}",
            idNuevo, idExpediente);

    return idNuevo != null ? idNuevo.intValue() : null;
}
}