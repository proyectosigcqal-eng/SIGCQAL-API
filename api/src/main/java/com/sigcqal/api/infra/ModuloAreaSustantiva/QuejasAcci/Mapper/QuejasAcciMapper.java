package com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAcci.Mapper;


import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejasAcci.Model.QuejasAcci;
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAcci.Entity.QuejasAcciEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Entity.QuejaEntity;

import com.sigcqal.api.infra.ModuloAreaSustantiva.OficioNotificacion.Entity.OficioNotificacionEntity;

import org.springframework.stereotype.Component;

@Component
public class QuejasAcciMapper {

    public QuejasAcciEntity toEntity(QuejasAcci domain) {
        QuejasAcciEntity e = new QuejasAcciEntity();
        if (domain.getId() != null) {
            e.setIdAcci(domain.getId().intValue());
        }

        if (domain.getIdQueja() != null) {
            QuejaEntity queja = new QuejaEntity();
            queja.setIdQueja(domain.getIdQueja().intValue());
            e.setQueja(queja);
        }

        if (domain.getIdOficioNotificacion() != null) {
            OficioNotificacionEntity oficio = new OficioNotificacionEntity();
            oficio.setId(domain.getIdOficioNotificacion().longValue());         
            e.setOficioNotificacion(oficio);
        }
        e.setJustificacionInvestigacion(domain.getJustificacionInvestigacion());
        e.setNuevosRequerimientosAutoridad(domain.getNuevosRequerimientosAutoridad());
        e.setPlazoDiasHabiles(domain.getPlazoDiasHabiles());
        e.setFechaEmisionAcci(domain.getFechaEmisionAcci());
        e.setRutaPdfAcci(domain.getRutaPdfAcci());
        e.setConcluido(domain.getConcluido());
        e.setFechaConclusion(domain.getFechaConclusion());
        return e;
    }

    public QuejasAcci toDomain(QuejasAcciEntity e) {
        return QuejasAcci.builder()
                .id(e.getIdAcci() != null ? e.getIdAcci().longValue() : null)
                .idQueja(e.getQueja() != null ? e.getQueja().getIdQueja().longValue() : null)
                .idOficioNotificacion(e.getOficioNotificacion() != null ? e.getOficioNotificacion().getId().longValue() : null)
                .justificacionInvestigacion(e.getJustificacionInvestigacion())
                .nuevosRequerimientosAutoridad(e.getNuevosRequerimientosAutoridad())
                .plazoDiasHabiles(e.getPlazoDiasHabiles())
                .fechaEmisionAcci(e.getFechaEmisionAcci())
                .rutaPdfAcci(e.getRutaPdfAcci())
                .concluido(e.getConcluido())
                .fechaConclusion(e.getFechaConclusion())
                .build();
    }
}