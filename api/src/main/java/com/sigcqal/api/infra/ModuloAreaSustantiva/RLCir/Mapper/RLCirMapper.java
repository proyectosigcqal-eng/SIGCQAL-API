package com.sigcqal.api.infra.ModuloAreaSustantiva.RLCir.Mapper;

import org.springframework.stereotype.Component;
import com.sigcqal.api.domain.ModuloAreaSustantiva.RLCir.Model.RLCir;
import com.sigcqal.api.infra.ModuloAreaSustantiva.RLCir.Entity.RLCirEntity;
import com.sigcqal.api.web.ModuloAreaSustantiva.RLCir.Dto.RLCirResponseDTO;

@Component
public class RLCirMapper {

    public RLCirEntity toEntity(RLCir domain) {
        if (domain == null) return null;

        RLCirEntity entity = new RLCirEntity();
        if (domain.getIdRlCir() != null) {
            entity.setIdRlCir(domain.getIdRlCir());
        }
        entity.setIdExpediente(domain.getIdExpediente());
        entity.setFechaEmision(domain.getFechaEmision());
        entity.setMotivos(domain.getMotivos());
        entity.setArticulos(domain.getArticulos());
        entity.setObservaciones(domain.getObservaciones());
        entity.setIdAsesorRemitente(domain.getIdAsesorRemitente());
        entity.setIdAsesorRecibe(domain.getIdAsesorRecibe());
        entity.setDirector(domain.getDirector());
        entity.setRutaPdfRlCir(domain.getRutaPdfRlCir());

        return entity;
    }

    public RLCir toDomain(RLCirEntity entity) {
        if (entity == null) return null;

        RLCir domain = new RLCir();
        domain.setIdRlCir(entity.getIdRlCir());
        domain.setIdExpediente(entity.getIdExpediente());
        domain.setFechaEmision(entity.getFechaEmision());
        domain.setMotivos(entity.getMotivos());
        domain.setArticulos(entity.getArticulos());
        domain.setObservaciones(entity.getObservaciones());
        domain.setIdAsesorRemitente(entity.getIdAsesorRemitente());
        domain.setIdAsesorRecibe(entity.getIdAsesorRecibe());
        domain.setDirector(entity.getDirector());
        domain.setRutaPdfRlCir(entity.getRutaPdfRlCir());

        return domain;
    }

    public RLCirResponseDTO toResponse(RLCir domain) {
        if (domain == null) return null;

        RLCirResponseDTO response = new RLCirResponseDTO();
        response.setIdRlCir(domain.getIdRlCir());
        response.setIdExpediente(domain.getIdExpediente());
        response.setFechaEmision(domain.getFechaEmision());
        response.setMotivos(domain.getMotivos());
        response.setArticulos(domain.getArticulos());
        response.setObservaciones(domain.getObservaciones());
        response.setIdAsesorRemitente(domain.getIdAsesorRemitente());
        response.setIdAsesorRecibe(domain.getIdAsesorRecibe());
        response.setDirector(domain.getDirector());
        response.setRutaPdfRlCir(domain.getRutaPdfRlCir());

        return response;
    }
}