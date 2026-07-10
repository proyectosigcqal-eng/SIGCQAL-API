package com.sigcqal.api.application.ModuloAreaSustantiva.OficioNotificacion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sigcqal.api.application.ModuloCorrespondencia.Documento.GeneradorDocumentoService;
import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.OficioNotificacion.Model.OficioNotificacion;
import com.sigcqal.api.domain.ModuloAreaSustantiva.OficioNotificacion.Port.OficioNotificacionRepositoryPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Queja.Model.EstatusQuejaIds;
import com.sigcqal.api.web.ModuloAreaSustantiva.OficioNotificacion.Dto.OficioNotificacionResponseDTO;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Repository.QuejaJPARepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OficioNotificacionService {

    private final FileUploadPort                   fileUploadPort;
    private final GeneradorDocumentoService        generadorDocumentoService;
    private final OficioNotificacionRepositoryPort oficioRepo;
    private final QuejaJPARepository               quejaJpaRepository;

    @Transactional // ← AGREGADO: sin esto, actualizarEstatusQueja truena
    public OficioNotificacionResponseDTO generarOficio(
            String  folioExpediente,
            String  numOficio,
            Integer idAutoridad,
            String  nombreAutoridad,
            String  nombreContribuyente,
            String  fechaAcuerdo,
            String  fundamento,
            String  inicialesAsesor,
            String  tipoAcuerdo) {

        System.out.println(">>> [INICIO generarOficio] folioExpediente recibido: " + folioExpediente);
        System.out.println(">>> numOficio recibido: " + numOficio);
        System.out.println(">>> tipoAcuerdo recibido: " + tipoAcuerdo);

        try {
            Map<String, String> variables = Map.ofEntries(
                Map.entry("{{NUM_OFICIO}}",         nvl(numOficio,           "[OFICIO]")),
                Map.entry("{{FECHA}}",              generadorDocumentoService.fechaActual()),
                Map.entry("{{NUM_EXPEDIENTE}}",     nvl(folioExpediente,     "[EXPEDIENTE]")),
                Map.entry("{{TIPO_ACUERDO}}", nvl(tipoAcuerdo, "[TIPO ACUERDO]")),
                Map.entry("{{CONTRIBUYENTE}}",      nvl(nombreContribuyente, "[CONTRIBUYENTE]")),
                Map.entry("{{AUTORIDAD}}",          nvl(nombreAutoridad,     "[AUTORIDAD]")),
                Map.entry("{{FECHA_ACUERDO}}",      nvl(fechaAcuerdo,        "[FECHA ACUERDO]")),
                Map.entry("{{FUNDAMENTO_LEGAL}}",   nvl(fundamento,          defaultFundamento())),
                Map.entry("{{INICIALES_ASESOR}}",   nvl(inicialesAsesor,     ""))
            );

            byte[] bytes = generadorDocumentoService
                    .generarDesPlantilla("plantilla_oficio_notificacion.docx", variables);

            String nombreArchivo = "OFICIO_" + folioExpediente
                    + "_" + System.currentTimeMillis() + ".docx";
            String ruta = fileUploadPort.guardarArchivoExpediente(bytes, nombreArchivo);

            OficioNotificacion guardado = oficioRepo.guardar(
                OficioNotificacion.builder()
                    .folioExpediente(folioExpediente)
                    .idAutoridad(idAutoridad)
                    .numOficio(numOficio)
                    .fechaAcuerdo(fechaAcuerdo)
                    .fundamento(nvl(fundamento, defaultFundamento()))
                    .inicialesAsesor(inicialesAsesor)
                    .tipoAcuerdo(tipoAcuerdo)
                    .rutaPdf(ruta)
                    .fechaGeneracion(LocalDateTime.now())
                    .build());

            quejaJpaRepository.findIdExpedienteByFolio(folioExpediente)
                .ifPresent(idExpediente ->
                    quejaJpaRepository.actualizarEstatusQueja(idExpediente, EstatusQuejaIds.OFICIO_EMITIDO));

            return OficioNotificacionResponseDTO.builder()
                    .id(guardado.getId())
                    .url(ruta)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Error al generar el oficio: " + e.getMessage(), e);
        }
    }

    public List<OficioNotificacion> listarPorFolio(String folio) {
        return oficioRepo.buscarPorFolio(folio);
    }

    private String nvl(String v, String fallback) {
        return (v != null && !v.isBlank()) ? v : fallback;
    }

    private String defaultFundamento() {
        return "Lo anterior, con fundado en los artículos 25 fracciones III, IV y IX; " +
               "33 fracción I, de la Ley de los Derechos y Defensa del Contribuyente " +
               "del Estado de Zacatecas y sus Municipios; 19, 21 fracción V, 25 fracción I " +
               "y 27 de los Lineamientos Generales de Actuación de la Comisión Estatal de " +
               "la Defensa del Contribuyente.";
    }

    public record ArchivoDescarga(String nombreArchivo, byte[] contenido) {}

public Optional<ArchivoDescarga> obtenerArchivoPorFolio(String folio) {
    List<OficioNotificacion> lista = oficioRepo.buscarPorFolio(folio);
    if (lista == null || lista.isEmpty()) return Optional.empty();

    OficioNotificacion ultimo = lista.stream()
        .max(Comparator.comparing(OficioNotificacion::getId))
        .orElse(null);
    if (ultimo == null || ultimo.getRutaPdf() == null || ultimo.getRutaPdf().isBlank()) {
        return Optional.empty();
    }

    String nombreArchivo = extraerNombreArchivo(ultimo.getRutaPdf());
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
}