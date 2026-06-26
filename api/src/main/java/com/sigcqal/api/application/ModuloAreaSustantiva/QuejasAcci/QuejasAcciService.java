package com.sigcqal.api.application.ModuloAreaSustantiva.QuejasAcci;


import com.sigcqal.api.application.ModuloCorrespondencia.Documento.GeneradorDocumentoService;
import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Queja.Model.EstatusQuejaIds;
import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejasAcci.Model.QuejasAcci;
import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejasAcci.Port.QuejasAcciRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Repository.QuejaJPARepository;
import com.sigcqal.api.web.ModuloAreaSustantiva.QuejasAcci.Dto.QuejasAcciRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.QuejasAcci.Dto.QuejasAcciResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuejasAcciService {

    private final QuejasAcciRepositoryPort port;
    private final FileUploadPort           fileUploadPort;
    private final GeneradorDocumentoService generadorDocumentoService;
    private final QuejaJPARepository quejaJpaRepository; // ← nuevo

@Transactional
    public QuejasAcciResponseDTO guardar(QuejasAcciRequestDTO request) {

        Long idQueja = resolverIdQueja(request);
    Long idOficioAutoridad = resolverIdOficioAutoridad(request);
        // Genera el DOCX del ACCI
        String rutaPdf = null;
        try {
            Map<String, String> variables = Map.ofEntries(
                Map.entry("{{FOLIO_ACCI}}",            nvl(request.getFolioExpediente(), "[FOLIO]")),
                Map.entry("{{EXPEDIENTE}}",            nvl(request.getFolioExpediente(), "[EXPEDIENTE]")),
                Map.entry("{{CONTRIBUYENTE}}",         nvl(request.getContribuyente(), "[CONTRIBUYENTE]")),
                Map.entry("{{AUTORIDAD_FISCAL}}",      nvl(request.getDependencia(), "[AUTORIDAD]")),
                Map.entry("{{FECHA}}",                 generadorDocumentoService.fechaActual()),
                Map.entry("{{NUM_OFICIO_RECIBIDO}}",   nvl(request.getNumOficioRecibido(), "[OFICIO]")),
                Map.entry("{{FECHA_OFICIO}}",          nvl(request.getFechaOficio(), "[FECHA OFICIO]")),
                Map.entry("{{FECHA_RECEPCION}}",       nvl(request.getFechaRecepcion(), "[FECHA RECEPCION]")),
                Map.entry("{{ENCARGADO_DEPENDENCIA}}", nvl(request.getEncargadoDependencia(), "[ENCARGADO]")),
                Map.entry("{{DEPENDENCIA}}",           nvl(request.getDependencia(), "[DEPENDENCIA]")),
                Map.entry("{{FECHA_PROVEIDO}}",        nvl(request.getFechaProveido(), generadorDocumentoService.fechaActual())),
                Map.entry("{{DOCUMENTOS_ANEXOS}}",     nvl(request.getDocumentosAnexos(), "[DOCUMENTOS]")),
                Map.entry("{{TITULAR_REQUERIDO}}",     nvl(request.getTitularRequerido(), "[TITULAR]")),
                Map.entry("{{MOTIVOS_REQUERIMIENTO}}", nvl(request.getMotivosRequerimiento(), "[MOTIVOS]")),
                Map.entry("{{INICIALES_ASESOR}}",      nvl(request.getInicialesAsesor(), ""))
            );

            byte[] bytes = generadorDocumentoService
                    .generarDesPlantilla("plantilla_acci.docx", variables);

            String nombre = "ACCI_" + request.getFolioExpediente()
                    + "_" + System.currentTimeMillis() + ".docx";
            rutaPdf = fileUploadPort.guardarArchivoExpediente(bytes, nombre);
        } catch (Exception e) {
            throw new RuntimeException("Error generando ACCI: " + e.getMessage(), e);
        }

       QuejasAcci guardado = port.guardar(QuejasAcci.builder()
        .idQueja(idQueja) 
        .idOficioNotificacion(idOficioAutoridad)
        .justificacionInvestigacion(request.getDocumentosAnexos())
        .nuevosRequerimientosAutoridad(request.getMotivosRequerimiento())
        .plazoDiasHabiles(5)
        .fechaEmisionAcci(LocalDateTime.now())
        .rutaPdfAcci(rutaPdf)
        .concluido(false)
        .build());

                quejaJpaRepository.findIdExpedienteByFolio(request.getFolioExpediente())
    .ifPresent(idExpediente ->
        quejaJpaRepository.actualizarEstatusQueja(idExpediente, EstatusQuejaIds.ACCI_GENERADO));


        return toResponse(guardado, rutaPdf);
    }

    // ── Resuelve idQueja desde folioExpediente si el caller no lo manda ──
private Long resolverIdQueja(QuejasAcciRequestDTO request) {
    if (request.getIdQueja() != null) return request.getIdQueja();

    Integer idExpediente = quejaJpaRepository.findIdExpedienteByFolio(request.getFolioExpediente())
        .orElseThrow(() -> new RuntimeException(
            "No se encontró expediente para el folio: " + request.getFolioExpediente()));

    return quejaJpaRepository.findByExpediente_Id(idExpediente)
        .map(q -> q.getIdQueja().longValue())
        .orElseThrow(() -> new RuntimeException(
            "No se encontró queja para el expediente: " + idExpediente));
}

// ── Resuelve idOficioAutoridad (el Oficio ligado al ARI) desde folioExpediente ──
private Long resolverIdOficioAutoridad(QuejasAcciRequestDTO request) {
    if (request.getIdOficioAutoridad() != null) return request.getIdOficioAutoridad();

    return quejaJpaRepository.findIdOficioAutoridadByFolio(request.getFolioExpediente())
        .orElseThrow(() -> new RuntimeException(
            "No se encontró un Oficio de Notificación previo para el folio: " + request.getFolioExpediente()));
}

    public List<QuejasAcciResponseDTO> listarPorQueja(Long idQueja) {
        return port.findByIdQueja(idQueja)
                .stream().map(a -> toResponse(a, a.getRutaPdfAcci()))
                .collect(Collectors.toList());
    }

    private QuejasAcciResponseDTO toResponse(QuejasAcci a, String url) {
        return QuejasAcciResponseDTO.builder()
                .id(a.getId())
                .idQueja(a.getIdQueja())
                .rutaPdfAcci(a.getRutaPdfAcci())
                .url(url)
                .fechaEmisionAcci(a.getFechaEmisionAcci())
                .concluido(a.getConcluido())
                .build();
    }

    private String nvl(String v, String fallback) {
        return (v != null && !v.isBlank()) ? v : fallback;
    }

    public record ArchivoDescarga(String nombreArchivo, byte[] contenido) {}

public Optional<ArchivoDescarga> obtenerArchivoPorFolio(String folio) {
    Integer idExpediente = quejaJpaRepository
            .findIdExpedienteByFolio(folio).orElse(null);
    if (idExpediente == null) return Optional.empty();

    var quejaOpt = quejaJpaRepository.findByExpediente_Id(idExpediente);
    if (quejaOpt.isEmpty()) return Optional.empty();

    Long idQueja = quejaOpt.get().getIdQueja().longValue();
    List<QuejasAcci> lista = port.findByIdQueja(idQueja);
    if (lista == null || lista.isEmpty()) return Optional.empty();

    QuejasAcci ultimo = lista.stream()
            .max(Comparator.comparing(QuejasAcci::getId))
            .orElse(null);

    if (ultimo == null
            || ultimo.getRutaPdfAcci() == null
            || ultimo.getRutaPdfAcci().isBlank()) {
        return Optional.empty();
    }

    String nombreArchivo = extraerNombreArchivo(ultimo.getRutaPdfAcci());

    try {
        // ← Usa ruta absoluta igual que FileUploadAdapter
        Path root      = Paths.get(".").toAbsolutePath().normalize();
        Path filePath  = root.resolve("uploads/expedientes/").resolve(nombreArchivo);

        if (!Files.exists(filePath)) {
            // El archivo físico no existe — devuelve empty en lugar de array vacío
            return Optional.empty();
        }

        byte[] contenido = Files.readAllBytes(filePath);
        return Optional.of(new ArchivoDescarga(nombreArchivo, contenido));

    } catch (IOException e) {
        return Optional.empty();
    }
}
private String extraerNombreArchivo(String url) {
    if (url == null || url.isEmpty()) return "";
    return url.substring(url.lastIndexOf('/') + 1);
}
}