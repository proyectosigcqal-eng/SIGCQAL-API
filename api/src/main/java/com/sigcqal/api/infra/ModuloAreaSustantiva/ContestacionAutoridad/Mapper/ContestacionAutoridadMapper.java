package com.sigcqal.api.infra.ModuloAreaSustantiva.ContestacionAutoridad.Mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.ContestacionAutoridad.Model.ContestacionAutoridad;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ContestacionAutoridad.Entity.ContestacionAutoridadEntity;

@Component
public class ContestacionAutoridadMapper {

    public ContestacionAutoridadEntity toEntity(ContestacionAutoridad domain) {
        ContestacionAutoridadEntity e = new ContestacionAutoridadEntity();
        e.setFolioExpediente(domain.getFolioExpediente());
        e.setNumeroOficio(domain.getNumeroOficio());
        e.setIdAutoridad(domain.getIdAutoridad());
        e.setNombreTitular(domain.getNombreTitular());
        e.setRutaPdfInforme(domain.getRutaPdfInforme());
        e.setObservaciones(domain.getObservaciones());
        e.setDecision(domain.getDecision());
        e.setFechaRegistro(LocalDateTime.now());
        e.setFechaOficio(domain.getFechaOficio());
        return e;
    }

    public ContestacionAutoridad toDomain(ContestacionAutoridadEntity e) {
        ContestacionAutoridad d = new ContestacionAutoridad();
        d.setId(e.getId());
        d.setFolioExpediente(e.getFolioExpediente());
        d.setNumeroOficio(e.getNumeroOficio());
        d.setIdAutoridad(e.getIdAutoridad());
        d.setNombreTitular(e.getNombreTitular());
        d.setRutaPdfInforme(e.getRutaPdfInforme());
        d.setObservaciones(e.getObservaciones());
        d.setDecision(e.getDecision());
        d.setFechaRegistro(e.getFechaRegistro());
        d.setFechaOficio(e.getFechaOficio());
        return d;
    }
}