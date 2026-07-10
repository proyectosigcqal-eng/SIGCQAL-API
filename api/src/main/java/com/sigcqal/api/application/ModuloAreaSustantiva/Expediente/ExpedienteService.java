package com.sigcqal.api.application.ModuloAreaSustantiva.Expediente;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigcqal.api.application.ModuloAreaSustantiva.Turnado.TurnadoService;
import com.sigcqal.api.application.exception.DuplicateResourceException;
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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExpedienteService {

    private final ExpedienteRepositoryPort port;
    private final ExpedienteMapper         mapper;
    private final FileUploadPort           fileUploadPort;
    private final TurnadoService           turnadoService;
    private final PersonaRepositoryPort    personaPort;

    @Transactional
    public ExpedienteResponseDTO guardar(ExpedienteRequestDTO request) {
        String folioGenerado = request.getFolioGobierno().trim();

        if (port.existsByFolio(folioGenerado)) {
            throw new DuplicateResourceException(
                    "El folio " + folioGenerado + " ya existe en el sistema. "
                            + "Ingrese un folio único.");
        }

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

        Expediente guardado = port.save(expediente);

        try {
            turnadoService.turnarAutomatico(folioGenerado);
        } catch (Exception e) {
            log.warn("[Turnado] No se pudo asignar asesor automáticamente " +
                     "al expediente {}: {}", folioGenerado, e.getMessage());
        }

        return mapper.toResponse(guardado);
    }

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

    public ExpedienteResponseDTO buscarPorFolio(String folio) {
        return port.findByFolio(folio)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException(
                        "Folio no encontrado: " + folio));
    }

    public List<ExpedienteResponseDTO> listarTodos() {
        return port.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    private void validarNoBloqueado(Expediente expediente, String folio) {
        if (Boolean.TRUE.equals(expediente.getBloqueado())) {
            throw new InvalidRequestException(
                    "El expediente " + folio + " está bloqueado. " +
                    "Fue cerrado automáticamente por vencimiento del plazo " +
                    "de prevención y no admite modificaciones.");
        }
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

        String estatus = entity.getEstatusExpediente() != null
                ? entity.getEstatusExpediente().getNombre()
                : "En Revisión";

        String tipoTramite = entity.getTipoTramite() != null
                ? entity.getTipoTramite().getNombre()
                : null;

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
                .analisisLegal(null)
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
