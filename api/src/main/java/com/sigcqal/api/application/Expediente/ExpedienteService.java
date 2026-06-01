package com.sigcqal.api.application.Expediente;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sigcqal.api.application.ClasificacionJuridica.ClasificacionJuridicaService;
import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.domain.Catalogo.Persona.Model.Persona;
import com.sigcqal.api.domain.Catalogo.Persona.Port.PersonaRepositoryPort;
import com.sigcqal.api.domain.Expediente.Model.Contribuyente;
import com.sigcqal.api.domain.Expediente.Model.Expediente;
import com.sigcqal.api.domain.Expediente.Port.ContribuyenteRepositoryPort;
import com.sigcqal.api.domain.Expediente.Port.ExpedienteRepositoryPort;
import com.sigcqal.api.infra.Expediente.Mapper.ExpedienteMapper;
import com.sigcqal.api.web.ClasificacionJuridica.DTO.ClasificacionJuridicaRequestDTO;
import com.sigcqal.api.web.Expediente.DTO.ExpedienteRequestDTO;
import com.sigcqal.api.web.Expediente.DTO.ExpedienteResponseDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpedienteService {

    private final ExpedienteRepositoryPort expedientePort;
    private final ContribuyenteRepositoryPort contribuyentePort;
    private final PersonaRepositoryPort personaPort;
    private final ExpedienteMapper mapper;
    private final ClasificacionJuridicaService clasificacionJuridicaService;

    @Transactional
    public ExpedienteResponseDTO guardar(ExpedienteRequestDTO request) {
        Long idContribuyente = resolverContribuyente(request);
        Long idSolicitante = resolverSolicitante(request);

        Expediente expediente = Expediente.builder()
                .folioGobierno(request.getFolioGobierno())
                .fechaSolicitud(request.getFechaSolicitud())
                .idMunicipio(request.getIdMunicipio())
                .idAsesor(request.getIdAsesor())
                .idContribuyente(idContribuyente)
                .idSolicitante(idSolicitante)
                .idTipoTramite(request.getIdTipoTramite())
                .idEstatusExpediente(request.getIdEstatusExpediente())
                .documentoAcreditaPersonalidad(request.getDocumentoAcreditaPersonalidad())
                .archivoDocumentoAcreditaPersonalidad(request.getArchivoDocumentoAcreditaPersonalidad())
                .build();

        Expediente guardado = expedientePort.save(expediente);
        calificarExpediente(request, guardado.getId());
        return mapper.toResponse(guardado);
    }

    private Long resolverContribuyente(ExpedienteRequestDTO request) {
        if (request.getIdContribuyente() != null && request.getIdContribuyente() > 0) {
            return contribuyentePort.findById(request.getIdContribuyente())
                    .map(Contribuyente::getId)
                    .orElseThrow(() -> new InvalidRequestException(
                            "Contribuyente no encontrado con id: " + request.getIdContribuyente()));
        }

        ExpedienteRequestDTO.ContribuyenteDatosDTO datos = request.getContribuyente();
        if (datos == null) {
            throw new InvalidRequestException("Se requiere idContribuyente o datos del contribuyente");
        }

        if (StringUtils.hasText(datos.getRfc())) {
            var existente = contribuyentePort.findByRfc(datos.getRfc().trim().toUpperCase());
            if (existente.isPresent()) {
                return existente.get().getId();
            }
        }

        Contribuyente nuevo = Contribuyente.builder()
                .rfc(datos.getRfc() != null ? datos.getRfc().trim().toUpperCase() : null)
                .razonSocial(datos.getRazonSocial())
                .idDireccion(datos.getIdDireccion())
                .correoElectronico(datos.getCorreoElectronico())
                .telefono(datos.getTelefono())
                .build();
        return contribuyentePort.save(nuevo).getId();
    }

    private Long resolverSolicitante(ExpedienteRequestDTO request) {
        if (request.getIdSolicitante() != null && request.getIdSolicitante() > 0) {
            return personaPort.findById(request.getIdSolicitante())
                    .map(Persona::getId)
                    .orElseThrow(() -> new InvalidRequestException(
                            "Solicitante no encontrado con id: " + request.getIdSolicitante()));
        }

        ExpedienteRequestDTO.SolicitanteDatosDTO datos = request.getSolicitante();
        if (datos == null) {
            throw new InvalidRequestException("Se requiere idSolicitante o datos del solicitante");
        }

        Persona persona = new Persona();
        persona.setIdDireccion(datos.getIdDireccion());
        persona.setNombre(datos.getNombre());
        persona.setApellidoPaterno(datos.getApellidoPaterno());
        persona.setApellidoMaterno(datos.getApellidoMaterno());
        persona.setCurp(datos.getCurp());
        persona.setTelefono(datos.getTelefono());
        return personaPort.save(persona).getId();
    }

    private void calificarExpediente(ExpedienteRequestDTO request, Integer idExpediente) {
        if (request.getTipoActo() == null && request.getIdAutoridad() == null) {
            return;
        }

        ClasificacionJuridicaRequestDTO clasificacion = new ClasificacionJuridicaRequestDTO();
        clasificacion.setIdExpediente(idExpediente);
        clasificacion.setTipoActo(request.getTipoActo());
        clasificacion.setIdAutoridad(request.getIdAutoridad());
        clasificacion.setIdEstatusDetalleExpediente(request.getIdEstatusDetalleExpediente());
        clasificacion.setIdTipoEntrada(request.getIdTipoEntrada());
        clasificacion.setCalificacionActo(request.getCalificacionActo());
        clasificacion.setProblematica(request.getProblematica());
        clasificacion.setSeguimientoAsesoria(request.getSeguimientoAsesoria());
        clasificacion.setMonto(request.getMonto());
        clasificacion.setFechaNotificacion(request.getFechaNotificacion());
        clasificacion.setNombreAutoridad(request.getNombreAutoridad());
        clasificacion.setNombreTipoActo(request.getNombreTipoActo());
        clasificacion.setNombreEstatusDetalle(request.getNombreEstatusDetalle());
        clasificacion.setNombreTipoEntrada(request.getNombreTipoEntrada());
        clasificacionJuridicaService.guardar(clasificacion);
    }
}
