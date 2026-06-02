package com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Model.Contribuyente;
import com.sigcqal.api.infra.Catalogo.Municipio.Entity.MunicipioEntity;
import com.sigcqal.api.infra.Catalogo.Persona.Entity.PersonaEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ContribuyenteEntity;
import com.sigcqal.api.web.Catalogo.Contribuyente.Dto.ContribuyenteDto;
import com.sigcqal.api.web.Catalogo.Persona.Dto.PersonaDTO;

@Component
public class ContribuyenteMapper {
    public ContribuyenteEntity toEntity(Contribuyente domain) {
        if (domain == null) {
            return null;
        }
        ContribuyenteEntity entity = new ContribuyenteEntity();
        entity.setId(domain.getId());
        entity.setFechaRegistroSistema(domain.getFechaRegistroSistema());
        entity.setObservacionesInternas(domain.getObservacionesInternas());

        if(domain.getIdPersona() != null) {
            PersonaEntity persona = new PersonaEntity();
            persona.setId(domain.getIdPersona().longValue());
            entity.setPersona(persona);
        }
      
        return entity;
    }

    public Contribuyente toDomain(ContribuyenteEntity entity) {
        if (entity == null) {
            return null;
        }
        Contribuyente domain = new Contribuyente();
        domain.setId(entity.getId());
        domain.setFechaRegistroSistema(entity.getFechaRegistroSistema());
        domain.setObservacionesInternas(entity.getObservacionesInternas());
        if(entity.getPersona() != null) {
            domain.setIdPersona(entity.getPersona().getId().intValue());
        }
        return domain;
    }

    public ContribuyenteDto toResponse(Contribuyente domain){
        if(domain == null) {
            return null;
        }
        ContribuyenteDto dto = new ContribuyenteDto();
        dto.setId(domain.getId());
        dto.setIdPersona(domain.getIdPersona());
        dto.setFechaRegistroSistema(domain.getFechaRegistroSistema());
        dto.setObservacionesInternas(domain.getObservacionesInternas());
        return dto;
    }
}