package com.sigcqal.api.application.ModuloAreaSustantiva.ResolucionFinal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Model.ResolucionFinal;
import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Port.ResolucionFinalRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Mapper.ResolucionFinalMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Dto.ResolucionFinalRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Dto.ResolucionFinalResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResolucionFinalService {

    private final ResolucionFinalRepositoryPort port;
    private final ResolucionFinalMapper         mapper;
    private final OficioResolucionFinalGenerator oficioGenerator;

    // -----------------------------------------------------------------------
    // GUARDAR
    // -----------------------------------------------------------------------
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
    // GENERAR OFICIO (.docx) A PARTIR DE LA PLANTILLA
    // -----------------------------------------------------------------------
    public byte[] generarOficio(Integer idResolucionFinal) {
        ResolucionFinal resolucionFinal = port.findById(idResolucionFinal)
                .orElseThrow(() -> new RuntimeException(
                        "Resolución final no encontrada: " + idResolucionFinal));

        Map<String, String> placeholders = construirPlaceholders(resolucionFinal);

        try {
            return oficioGenerator.generar(placeholders);
        } catch (Exception e) {
            log.error("[ResolucionFinal] Error generando oficio para id {}: {}",
                    idResolucionFinal, e.getMessage());
            throw new RuntimeException("No se pudo generar el oficio: " + e.getMessage(), e);
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

    /**
     * Arma los valores que reemplazan los {{PLACEHOLDERS}} de plantilla_oficio.docx.
     * NOTA: NOMBRE_EMISOR, AREA_DESTINATARIO, INSTRUCCION, NOMBRE_FIRMANTE y
     * AREA_FIRMANTE dependen de datos que hoy no viven en resolucion_final
     * (folio/expediente/contribuyente vienen de otras tablas — ver puntos
     * pendientes al final de este archivo). Se dejan con valores por defecto
     * o vacíos hasta integrar esos joins.
     */
    private Map<String, String> construirPlaceholders(ResolucionFinal resolucionFinal) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "MX"));
        LocalDate fecha = resolucionFinal.getFechaEmisionResolucion();
        String fechaTexto = fecha != null ? fecha.format(formatter) : "";

        String credito = resolucionFinal.getFolioCredito() != null
                ? resolucionFinal.getFolioCredito()
                : (resolucionFinal.getNumeroCredito() != null
                        ? String.valueOf(resolucionFinal.getNumeroCredito())
                        : "");

        return Map.ofEntries(
                Map.entry("FOLIO", "RF-" + resolucionFinal.getIdResolucionFinal()),
                Map.entry("ASUNTO", resolucionFinal.getConceptoCobro() != null
                        ? resolucionFinal.getConceptoCobro() : ""),
                Map.entry("FECHA", fechaTexto),
                Map.entry("AREA_DESTINATARIO", ""),     // pendiente: viene de id_expediente -> autoridad
                Map.entry("NOMBRE_EMISOR", ""),         // pendiente: viene del usuario/firmante en sesión
                Map.entry("INSTRUCCION", construirInstruccion(resolucionFinal, credito)),
                Map.entry("NOMBRE_FIRMANTE", ""),       // pendiente: catálogo de firmantes
                Map.entry("AREA_FIRMANTE", "Comisión Estatal de la Defensa del Contribuyente")
        );
    }

    private String construirInstruccion(ResolucionFinal resolucionFinal, String credito) {
        StringBuilder sb = new StringBuilder();
        sb.append("relativo a ").append(resolucionFinal.getConceptoCobro() != null
                ? resolucionFinal.getConceptoCobro() : "");
        if (!credito.isBlank()) {
            sb.append(", con número de crédito ").append(credito);
        }
        if (resolucionFinal.getContactoVia() != null && !resolucionFinal.getContactoVia().isBlank()) {
            sb.append(". Contacto realizado vía ").append(resolucionFinal.getContactoVia());
        }
        sb.append(".");
        return sb.toString();
    }
}