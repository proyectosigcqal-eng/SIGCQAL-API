package com.sigcqal.api.application.ModuloAreaSustantiva.Expediente;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sigcqal.api.application.ModuloAreaSustantiva.Turnado.TurnadoService;
import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.domain.Catalogo.Persona.Port.PersonaRepositoryPort;
import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Model.Expediente;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Port.ExpedienteRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Mapper.ExpedienteMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.DetalleAsesoria.Dto.DetalleAsesoriaResponseDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.Expediente.DTO.ExpedienteRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.Expediente.DTO.ExpedienteResponseDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpedienteService {

    private final ExpedienteRepositoryPort port;
    private final ExpedienteMapper         mapper;
    private final FileUploadPort           fileUploadPort;
    private final TurnadoService           turnadoService;
    private final PersonaRepositoryPort    personaPort;

    // -----------------------------------------------------------------------
    // GUARDAR
    // -----------------------------------------------------------------------
    @Transactional
    public ExpedienteResponseDTO guardar(ExpedienteRequestDTO request) {
        String folioGenerado = generarFolioAutomatico();

        Expediente expediente = Expediente.builder()
                .folioGobierno(folioGenerado)
                .fechaSolicitud(request.getFechaSolicitud())
                .idMunicipio(request.getIdMunicipio())
                .idAsesor(request.getIdAsesor())
                .idContribuyente(request.getIdContribuyente())
                .idSolicitante(request.getIdSolicitante())
                .idRepresentanteLegal(request.getIdRepresentanteLegal())
                .idTipoTramite(request.getIdTipoTramite())
                .idEstatusExpediente(request.getIdEstatusExpediente())
                .documentoAcreditaPersonalidad(request.getDocumentoAcreditaPersonalidad())
                .archivoDocumentoAcreditaPersonalidad(
                        request.getArchivoDocumentoAcreditaPersonalidad())
                .build();

        // 1. Guardar el expediente primero
        Expediente guardado = port.save(expediente);

        // 2. Turnar automáticamente — si falla no revienta el guardado
        try {
            turnadoService.turnarAutomatico(folioGenerado);
        } catch (Exception e) {
            log.warn("[Turnado] No se pudo asignar asesor automáticamente " +
                     "al expediente {}: {}", folioGenerado, e.getMessage());
        }

        return mapper.toResponse(guardado);
    }

    // -----------------------------------------------------------------------
    // GUARDAR DOCUMENTO
    // -----------------------------------------------------------------------
    @Transactional
    public ExpedienteResponseDTO guardarDocumentoPersonalidad(
            String folio, byte[] archivo) {

        Expediente expediente = port.findByFolio(folio)
                .orElseThrow(() -> new RuntimeException(
                        "Expediente no encontrado: " + folio));

        validarNoBloqueado(expediente, folio);

        String nombreArchivo = "EXP_" + folio + "_DOC_PERSONALIDAD.pdf";
        String url = fileUploadPort.guardarArchivoExpediente(archivo, nombreArchivo);
        expediente.setArchivoDocumentoAcreditaPersonalidad(url);

        return mapper.toResponse(port.save(expediente));
    }

    // -----------------------------------------------------------------------
    // BUSCAR POR FOLIO
    // -----------------------------------------------------------------------
    public ExpedienteResponseDTO buscarPorFolio(String folio) {
        return port.findByFolio(folio)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException(
                        "Folio no encontrado: " + folio));
    }

    // -----------------------------------------------------------------------
    // LISTAR TODOS
    // -----------------------------------------------------------------------
    public List<ExpedienteResponseDTO> listarTodos() {
        return port.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    // -----------------------------------------------------------------------
    // HELPERS PRIVADOS
    // -----------------------------------------------------------------------
    private void validarNoBloqueado(Expediente expediente, String folio) {
        if (Boolean.TRUE.equals(expediente.getBloqueado())) {
            throw new InvalidRequestException(
                    "El expediente " + folio + " está bloqueado. " +
                    "Fue cerrado automáticamente por vencimiento del plazo " +
                    "de prevención y no admite modificaciones.");
        }
    }

    private String generarFolioAutomatico() {
        LocalDate now    = LocalDate.now();
        String yy        = String.valueOf(now.getYear()).substring(2);
        String mm        = String.format("%02d", now.getMonthValue());
        String prefix    = yy + mm;
        int    nextSeq   = 1;

        Optional<Expediente> last = port.findTopByFolioPrefix(prefix);
        if (last.isPresent()
                && last.get().getFolioGobierno() != null
                && last.get().getFolioGobierno().length() > prefix.length()) {
            try {
                nextSeq = Integer.parseInt(
                        last.get().getFolioGobierno()
                            .substring(prefix.length())) + 1;
            } catch (NumberFormatException e) {
                nextSeq = 1;
            }
        }
        return prefix + String.format("%05d", nextSeq);
    }

  public DetalleAsesoriaResponseDTO obtenerDetalleCompletoPorFolio(String folio) {

    ExpedienteEntity entity = port.findEntityByFolio(folio)
            .orElseThrow(() -> new RuntimeException("Folio no encontrado: " + folio));

   String nombreContribuyente = null;
   String identificacionOficialContribuyente = null;
if (entity.getContribuyente() != null 
        && entity.getContribuyente().getPersona() != null) {
    var p = entity.getContribuyente().getPersona();
    nombreContribuyente = String.join(" ",
        p.getNombre()          != null ? p.getNombre()          : "",
        p.getApellidoPaterno() != null ? p.getApellidoPaterno() : "",
        p.getApellidoMaterno() != null ? p.getApellidoMaterno() : ""
    ).trim();
    identificacionOficialContribuyente = p.getIdentificacionOficial();
}

    // ✅ Nombre del asesor — AsesorEntity solo tiene idPersona, necesitas buscarlo
  String nombreAsesor = null;
if (entity.getAsesor() != null && entity.getAsesor().getIdPersona() != null) {
    try {
        com.sigcqal.api.domain.Catalogo.Persona.Model.Persona personaAsesor = 
            personaPort.findById(entity.getAsesor().getIdPersona())
                .orElse(null);

        if (personaAsesor != null) {
            nombreAsesor = String.join(" ",
                personaAsesor.getNombre()          != null ? personaAsesor.getNombre()          : "",
                personaAsesor.getApellidoPaterno() != null ? personaAsesor.getApellidoPaterno() : "",
                personaAsesor.getApellidoMaterno() != null ? personaAsesor.getApellidoMaterno() : ""
            ).trim();
        }
    } catch (Exception e) {
        log.warn("No se pudo obtener persona del asesor: {}", e.getMessage());
    }
}

    // ✅ Estatus
    String estatus = entity.getEstatusExpediente() != null
            ? entity.getEstatusExpediente().getNombre()
            : "En Revisión";

    // ✅ Tipo de trámite
    String tipoTramite = entity.getTipoTramite() != null
            ? entity.getTipoTramite().getNombre()
            : null;

    // ✅ Municipio
    String municipio = entity.getMunicipio() != null
            ? entity.getMunicipio().getNombreMunicipio()
            : null;

    return DetalleAsesoriaResponseDTO.builder()
            .idExpediente(entity.getId().longValue())
            .folio(entity.getFolioGobierno())
            .fechaRegistro(entity.getFechaSolicitud() != null
                    ? entity.getFechaSolicitud().toString() : null)
            .contribuyente(nombreContribuyente)
            .identificacionOficial(identificacionOficialContribuyente)
            .autoridadResponsable(nombreAsesor)
            .estatusActual(estatus)
            .descripcionSintetica(tipoTramite)
            .progresoPorcentaje(calcularProgreso(estatus))
            .analisisLegal(null)  // se llena cuando hay detalle_asesoria
            .bitacora(null)
            .build();
}

private Integer calcularProgreso(String estatus) {
    if (estatus == null) return 0;
    return switch (estatus.toUpperCase()) {
        case "EN REVISIÓN", "EN REVISION" -> 10;
        case "EN CALIFICACIÓN", "EN CALIFICACION" -> 25;
        case "SEGUIMIENTO DE QUEJA" -> 50;
        case "EMISIÓN DE CIR", "EMISION DE CIR" -> 65;
        case "ARI EMITIDO" -> 75;
        case "OFICIO ENVIADO" -> 85;
        case "FINALIZADO", "CONCLUIDO" -> 100;
        default -> 0;
    };
}
}