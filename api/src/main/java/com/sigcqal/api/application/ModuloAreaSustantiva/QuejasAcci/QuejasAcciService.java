package com.sigcqal.api.application.ModuloAreaSustantiva.QuejasAcci;


import com.sigcqal.api.application.ModuloCorrespondencia.Documento.GeneradorDocumentoService;
import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejasAcci.Model.QuejasAcci;
import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejasAcci.Port.QuejasAcciRepositoryPort;
import com.sigcqal.api.web.ModuloAreaSustantiva.QuejasAcci.Dto.QuejasAcciRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.QuejasAcci.Dto.QuejasAcciResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuejasAcciService {

    private final QuejasAcciRepositoryPort port;
    private final FileUploadPort           fileUploadPort;
    private final GeneradorDocumentoService generadorDocumentoService;

    public QuejasAcciResponseDTO guardar(QuejasAcciRequestDTO request) {
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
                .idQueja(request.getIdQueja())
                .idOficioAutoridad(request.getIdOficioAutoridad())
                .justificacionInvestigacion(request.getDocumentosAnexos())
                .nuevosRequerimientosAutoridad(request.getMotivosRequerimiento())
                .plazoDiasHabiles(5)
                .fechaEmisionAcci(LocalDateTime.now())
                .rutaPdfAcci(rutaPdf)
                .concluido(false)
                .build());

        return toResponse(guardado, rutaPdf);
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
}