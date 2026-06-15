package com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Mapper;

import org.springframework.stereotype.Component;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Queja.Model.Queja;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Entity.QuejaEntity;
import com.sigcqal.api.web.ModuloAreaSustantiva.Queja.DTO.QuejaResponseDTO;

@Component
public class QuejaMapper {

    public Queja toDomain(QuejaEntity entity) {
        if (entity == null) return null;

        Queja domain = new Queja();
        domain.setIdQueja(entity.getIdQueja());
        domain.setRequisitoIdentificacion(entity.getRequisitoIdentificacion());
        domain.setRequisitoActosFiscales(entity.getRequisitoActosFiscales());
        domain.setRequisitoNarrativaClara(entity.getRequisitoNarrativaClara());
        domain.setRequisitoCompetenciaCedecon(entity.getRequisitoCompetenciaCedecon());
        domain.setFechaRegistro(entity.getFechaRegistro());
        domain.setUltimaActualizacion(entity.getUltimaActualizacion());

        if (entity.getExpediente() != null) {
            domain.setIdExpediente(entity.getExpediente().getId());
        }
        if (entity.getAsesor() != null) {
            domain.setIdAsesor(entity.getAsesor().getIdAsesor());
        }
        if (entity.getAutoridad() != null) {
            domain.setIdAutoridad(entity.getAutoridad().getId());
        }
        if (entity.getEstatusQueja() != null) {
            domain.setIdEstatusQueja(entity.getEstatusQueja());
        }

        return domain;
    }

    public QuejaResponseDTO toResponse(Queja domain) {
        if (domain == null) return null;

        QuejaResponseDTO dto = new QuejaResponseDTO();
        dto.setIdQueja(domain.getIdQueja());
        dto.setIdExpediente(domain.getIdExpediente());
        dto.setIdAsesor(domain.getIdAsesor());
        dto.setIdAutoridad(domain.getIdAutoridad());
        dto.setIdEstatusQueja(domain.getIdEstatusQueja());
        dto.setRequisitoIdentificacion(domain.getRequisitoIdentificacion());
        dto.setRequisitoActosFiscales(domain.getRequisitoActosFiscales());
        dto.setRequisitoNarrativaClara(domain.getRequisitoNarrativaClara());
        dto.setRequisitoCompetenciaCedecon(domain.getRequisitoCompetenciaCedecon());
        dto.setFechaRegistro(domain.getFechaRegistro());
        dto.setUltimaActualizacion(domain.getUltimaActualizacion());
        
        return dto;
    }
}