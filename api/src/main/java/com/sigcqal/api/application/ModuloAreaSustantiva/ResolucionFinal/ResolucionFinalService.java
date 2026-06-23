package com.sigcqal.api.application.ModuloAreaSustantiva.ResolucionFinal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigcqal.api.application.ModuloCorrespondencia.Documento.GeneradorDocumentoService;
import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Queja.Model.EstatusQuejaIds;
import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Model.ResolucionFinal;
import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Port.ResolucionFinalRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Repository.QuejaJPARepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Mapper.ResolucionFinalMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Dto.ResolucionFinalDatosPreviosDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Dto.ResolucionFinalRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Dto.ResolucionFinalResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResolucionFinalService {

    private final ResolucionFinalRepositoryPort port;
    private final ResolucionFinalMapper          mapper;
    private final FileUploadPort                 fileUploadPort;
    private final GeneradorDocumentoService      generadorDocumentoService;
    private final QuejaJPARepository quejaJpaRepository; // ← nuevo

    private static final String NOMBRE_PLANTILLA = "plantilla_resolucion_final.docx";

    // -----------------------------------------------------------------------
    // GUARDAR
    // -----------------------------------------------------------------------
    @Transactional
    public ResolucionFinalResponseDTO guardar(ResolucionFinalRequestDTO request) {

        validarUnicaResolucionPorExpediente(request.getIdExpediente());

        ResolucionFinal resolucionFinal = ResolucionFinal.builder()
                .fechaEmisionResolucion(request.getFechaEmisionResolucion())
                .conceptoCobro(request.getConceptoCobro())
                .contactoVia(request.getContactoVia())
                .numeroCredito(request.getNumeroCredito())
                .folioCredito(request.getFolioCredito())
                .idExpediente(request.getIdExpediente())
                .idAri(request.getIdAri())
                .idQuejaRespuestaAutoridad(request.getIdQuejaRespuestaAutoridad())
                .idEstatusQueja(request.getIdEstatusQueja())
                .idEstatusExpediente(request.getIdEstatusExpediente())
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
}