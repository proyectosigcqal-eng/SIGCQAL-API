package com.sigcqal.api.application.ModuloAreaSustantiva.ContestacionAutoridad;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.sigcqal.api.application.ModuloCorrespondencia.Documento.GeneradorDocumentoService;
import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.ContestacionAutoridad.Model.ContestacionAutoridad;
import com.sigcqal.api.domain.ModuloAreaSustantiva.ContestacionAutoridad.Port.ContestacionAutoridadPort;
import com.sigcqal.api.web.ModuloAreaSustantiva.ContestacionAutoridad.Dto.ContestacionAutoridadResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContestacionAutoridadService {

    private final ContestacionAutoridadPort port;
    private final FileUploadPort fileUploadPort;
    private final GeneradorDocumentoService generadorDocumentoService;

    // ── 1. Guardar la contestación recibida ──────────────────────────────────
    public ContestacionAutoridadResponseDTO guardar(
            String folioExpediente,
            String numeroOficio,
            String nombreTitular,
            String observaciones,
            String decision,
            byte[] archivoPDF,
            String nombreArchivo) {

        String rutaPdf = null;
        if (archivoPDF != null && archivoPDF.length > 0) {
            rutaPdf = fileUploadPort.guardarArchivoExpediente(archivoPDF, nombreArchivo);
        }

        ContestacionAutoridad contestacion = new ContestacionAutoridad();
        contestacion.setFolioExpediente(folioExpediente);
        contestacion.setNumeroOficio(numeroOficio);
        contestacion.setNombreTitular(nombreTitular);
        contestacion.setObservaciones(observaciones);
        contestacion.setDecision(decision);
        contestacion.setRutaPdfInforme(rutaPdf);

        ContestacionAutoridad saved = port.guardar(contestacion);

        return ContestacionAutoridadResponseDTO.builder()
            .id(saved.getId())
            .folioExpediente(saved.getFolioExpediente())
            .numeroOficio(saved.getNumeroOficio())
            .nombreTitular(saved.getNombreTitular())
            .rutaPdfInforme(saved.getRutaPdfInforme())
            .observaciones(saved.getObservaciones())
            .decision(saved.getDecision())
            .build();
    }

    // ── 2. Generar el DOCX del ACCI ──────────────────────────────────────────
    public String generarACCI(
            String folioAcci,
            String expediente,
            String contribuyente,
            String autoridadFiscal,
            String numOficioRecibido,
            String fechaOficio,
            String fechaRecepcion,
            String encargadoDependencia,
            String dependencia,
            String fechaProveido,
            String documentosAnexos,
            String titularRequerido,
            String motivosRequerimiento,
            String inicialesAsesor) {

        try {
            Map<String, String> variables = Map.ofEntries(
                Map.entry("{{FOLIO_ACCI}}",            nvl(folioAcci, "[FOLIO]")),
                Map.entry("{{EXPEDIENTE}}",            nvl(expediente, "[EXPEDIENTE]")),
                Map.entry("{{CONTRIBUYENTE}}",         nvl(contribuyente, "[CONTRIBUYENTE]")),
                Map.entry("{{AUTORIDAD_FISCAL}}",      nvl(autoridadFiscal, "[AUTORIDAD]")),
                Map.entry("{{FECHA}}",                 generadorDocumentoService.fechaActual()),
                Map.entry("{{NUM_OFICIO_RECIBIDO}}",   nvl(numOficioRecibido, "[OFICIO]")),
                Map.entry("{{FECHA_OFICIO}}",          nvl(fechaOficio, "[FECHA OFICIO]")),
                Map.entry("{{FECHA_RECEPCION}}",       nvl(fechaRecepcion, "[FECHA RECEPCION]")),
                Map.entry("{{ENCARGADO_DEPENDENCIA}}", nvl(encargadoDependencia, "[ENCARGADO]")),
                Map.entry("{{DEPENDENCIA}}",           nvl(dependencia, "[DEPENDENCIA]")),
                Map.entry("{{FECHA_PROVEIDO}}",        nvl(fechaProveido, "[FECHA PROVEIDO]")),
                Map.entry("{{DOCUMENTOS_ANEXOS}}",     nvl(documentosAnexos, "[DOCUMENTOS]")),
                Map.entry("{{TITULAR_REQUERIDO}}",     nvl(titularRequerido, "[TITULAR]")),
                Map.entry("{{MOTIVOS_REQUERIMIENTO}}", nvl(motivosRequerimiento, "[MOTIVOS]")),
                Map.entry("{{INICIALES_ASESOR}}",      nvl(inicialesAsesor, ""))
            );

            byte[] bytes = generadorDocumentoService
                .generarDesPlantilla("plantilla_acci.docx", variables);

            String nombreArchivo = "ACCI_" + folioAcci + "_" + System.currentTimeMillis() + ".docx";
            return fileUploadPort.guardarArchivoExpediente(bytes, nombreArchivo);

        } catch (Exception e) {
            System.err.println("Error generando ACCI: " + e.getMessage());
            throw new RuntimeException("Error al generar el ACCI: " + e.getMessage(), e);
        }
    }

    private String nvl(String value, String fallback) {
        return (value != null && !value.isBlank()) ? value : fallback;
    }
}