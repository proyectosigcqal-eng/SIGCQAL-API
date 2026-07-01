package com.sigcqal.api.infra.ModuloAreaSustantiva.QuejaRlCir.Mapper;

import org.springframework.stereotype.Component;
import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejaRlCir.Model.QuejaRlCir;
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejaRlCir.Entity.QuejaRlCirEntity;
import com.sigcqal.api.web.ModuloAreaSustantiva.QuejaRlCir.Dto.QuejaRlCirResponseDTO;

@Component
public class QuejaRlCirMapper {

    public QuejaRlCirEntity toEntity(QuejaRlCir domain) {
        if (domain == null) return null;

        QuejaRlCirEntity entity = new QuejaRlCirEntity();
        if (domain.getIdQuejaRlCir() != null) {
            entity.setIdQuejaRlCir(domain.getIdQuejaRlCir());
        }
        entity.setIdResolucionFinal(domain.getIdResolucionFinal());
        entity.setFechaEmision(domain.getFechaEmision());
        entity.setMotivos(domain.getMotivos());
        entity.setArticulos(domain.getArticulos());
        entity.setObservaciones(domain.getObservaciones());
        entity.setOficio(domain.getOficio());
        entity.setIdAsesorRemitente(domain.getIdAsesorRemitente());
        entity.setIdAsesorRecibe(domain.getIdAsesorRecibe());
        entity.setDirector(domain.getDirector());
        entity.setRutaPdfQuejaRlCir(domain.getRutaPdfQuejaRlCir());

        return entity;
    }

    public QuejaRlCir toDomain(QuejaRlCirEntity entity) {
        if (entity == null) return null;

        QuejaRlCir domain = new QuejaRlCir();
        domain.setIdQuejaRlCir(entity.getIdQuejaRlCir());
        domain.setIdResolucionFinal(entity.getIdResolucionFinal());
        domain.setFechaEmision(entity.getFechaEmision());
        domain.setMotivos(entity.getMotivos());
        domain.setArticulos(entity.getArticulos());
        domain.setObservaciones(entity.getObservaciones());
        domain.setOficio(entity.getOficio());
        domain.setIdAsesorRemitente(entity.getIdAsesorRemitente());
        domain.setIdAsesorRecibe(entity.getIdAsesorRecibe());
        domain.setDirector(entity.getDirector());
        domain.setRutaPdfQuejaRlCir(entity.getRutaPdfQuejaRlCir());

        return domain;
    }

    public QuejaRlCirResponseDTO toResponse(QuejaRlCir domain) {
        if (domain == null) return null;

        QuejaRlCirResponseDTO response = new QuejaRlCirResponseDTO();
        response.setIdQuejaRlCir(domain.getIdQuejaRlCir());
        response.setIdResolucionFinal(domain.getIdResolucionFinal());
        response.setFechaEmision(domain.getFechaEmision());
        response.setMotivos(domain.getMotivos());
        response.setArticulos(domain.getArticulos());
        response.setObservaciones(domain.getObservaciones());
        response.setOficio(domain.getOficio());
        response.setIdAsesorRemitente(domain.getIdAsesorRemitente());
        response.setIdAsesorRecibe(domain.getIdAsesorRecibe());
        response.setDirector(domain.getDirector());
        response.setRutaPdfQuejaRlCir(domain.getRutaPdfQuejaRlCir());

        return response;
    }
}