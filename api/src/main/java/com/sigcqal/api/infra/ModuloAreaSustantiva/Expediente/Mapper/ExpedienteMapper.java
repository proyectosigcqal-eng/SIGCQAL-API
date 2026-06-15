package com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Model.Expediente;
import com.sigcqal.api.infra.Catalogo.Asesor.Entity.AsesorEntity;
import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Entity.EstatusExpedienteEntity;
import com.sigcqal.api.infra.Catalogo.Municipio.Entity.MunicipioEntity;
import com.sigcqal.api.infra.Catalogo.Persona.Entity.PersonaEntity;
import com.sigcqal.api.infra.Catalogo.TipoTramite.Entity.TipoTramiteEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ContribuyenteEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import com.sigcqal.api.web.ModuloAreaSustantiva.Expediente.DTO.ExpedienteResponseDTO;

@Component
public class ExpedienteMapper {

    public ExpedienteEntity toEntity(Expediente expediente) {
        ExpedienteEntity entity = new ExpedienteEntity();
        entity.setId(expediente.getId());
        entity.setFolioGobierno(expediente.getFolioGobierno());
        entity.setFechaSolicitud(expediente.getFechaSolicitud());
        entity.setDocumentoAcreditaPersonalidad(expediente.getDocumentoAcreditaPersonalidad());
        entity.setArchivoDocumentoAcreditaPersonalidad(expediente.getArchivoDocumentoAcreditaPersonalidad());

        if (expediente.getIdMunicipio() != null) {
            MunicipioEntity municipio = new MunicipioEntity();
            municipio.setId(expediente.getIdMunicipio());
            entity.setMunicipio(municipio);
        }
        if (expediente.getIdAsesor() != null) {
            AsesorEntity asesor = new AsesorEntity();
            asesor.setIdAsesor(expediente.getIdAsesor());
            entity.setAsesor(asesor);
        }
        if (expediente.getIdContribuyente() != null) {
            ContribuyenteEntity contribuyente = new ContribuyenteEntity();
            contribuyente.setId(expediente.getIdContribuyente());
            entity.setContribuyente(contribuyente);
        }
        if (expediente.getIdSolicitante() != null) {
            PersonaEntity solicitante = new PersonaEntity();
            solicitante.setId(expediente.getIdSolicitante());
            entity.setSolicitante(solicitante);
        }
        if (expediente.getIdRepresentanteLegal() != null) {
            PersonaEntity representanteLegal = new PersonaEntity();
            representanteLegal.setId(expediente.getIdRepresentanteLegal());
            entity.setRepresentanteLegal(representanteLegal);
        }
        if (expediente.getIdTipoTramite() != null) {
            TipoTramiteEntity tipoTramite = new TipoTramiteEntity();
            tipoTramite.setId(expediente.getIdTipoTramite());
            entity.setTipoTramite(tipoTramite);
        }
        if (expediente.getIdEstatusExpediente() != null) {
            EstatusExpedienteEntity estatus = new EstatusExpedienteEntity();
            estatus.setId(expediente.getIdEstatusExpediente());
            entity.setEstatusExpediente(estatus);
        }
        return entity;
    }

    public Expediente toDomain(ExpedienteEntity entity) {
        Expediente expediente = new Expediente();
        expediente.setId(entity.getId());
        expediente.setFolioGobierno(entity.getFolioGobierno());
        expediente.setFechaSolicitud(entity.getFechaSolicitud());
        expediente.setDocumentoAcreditaPersonalidad(entity.getDocumentoAcreditaPersonalidad());
        expediente.setArchivoDocumentoAcreditaPersonalidad(entity.getArchivoDocumentoAcreditaPersonalidad());
        expediente.setBloqueado(entity.getBloqueado());
        expediente.setFechaCierreAutomatico(entity.getFechaCierreAutomatico());

        if (entity.getMunicipio() != null) {
            expediente.setIdMunicipio(entity.getMunicipio().getId());
        }
        if (entity.getAsesor() != null) {
            expediente.setIdAsesor(entity.getAsesor().getIdAsesor());
        }
        if (entity.getContribuyente() != null) {
            expediente.setIdContribuyente(entity.getContribuyente().getId());
        }
        if (entity.getSolicitante() != null) {
            expediente.setIdSolicitante(entity.getSolicitante().getId());
        }
        if (entity.getRepresentanteLegal() != null) {
            expediente.setIdRepresentanteLegal(entity.getRepresentanteLegal().getId());
        }
        if (entity.getTipoTramite() != null) {
            expediente.setIdTipoTramite(entity.getTipoTramite().getId());
        }
        if (entity.getEstatusExpediente() != null) {
            expediente.setIdEstatusExpediente(entity.getEstatusExpediente().getId());
        }
        return expediente;
    }

    public ExpedienteResponseDTO toResponse(Expediente domain) {
        if (domain == null) {
            return null;
        }
        ExpedienteResponseDTO dto = new ExpedienteResponseDTO();
        dto.setFolioGobierno(domain.getFolioGobierno());
        dto.setFechaSolicitud(domain.getFechaSolicitud());
        dto.setIdMunicipio(domain.getIdMunicipio());
        dto.setIdAsesor(domain.getIdAsesor());
        dto.setIdContribuyente(domain.getIdContribuyente());
        dto.setIdSolicitante(domain.getIdSolicitante());
        dto.setIdRepresentanteLegal(domain.getIdRepresentanteLegal());
        dto.setIdTipoTramite(domain.getIdTipoTramite());
        dto.setIdEstatusExpediente(domain.getIdEstatusExpediente());
        dto.setDocumentoAcreditaPersonalidad(domain.getDocumentoAcreditaPersonalidad());
        dto.setArchivoDocumentoAcreditaPersonalidad(domain.getArchivoDocumentoAcreditaPersonalidad());
        return dto;
    }
}
