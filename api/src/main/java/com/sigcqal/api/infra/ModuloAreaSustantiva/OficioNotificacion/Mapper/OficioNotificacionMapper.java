package com.sigcqal.api.infra.ModuloAreaSustantiva.OficioNotificacion.Mapper;

import org.springframework.stereotype.Component;
import com.sigcqal.api.domain.ModuloAreaSustantiva.OficioNotificacion.Model.OficioNotificacion;
import com.sigcqal.api.infra.Catalogo.Autoridad.Entity.AutoridadEntity;

import com.sigcqal.api.infra.ModuloAreaSustantiva.OficioNotificacion.Entity.OficioNotificacionEntity;

@Component
public class OficioNotificacionMapper {

    public OficioNotificacionEntity toEntity(OficioNotificacion domain) {
        OficioNotificacionEntity entity = new OficioNotificacionEntity();

        entity.setFolioExpediente(domain.getFolioExpediente()); 

        if (domain.getIdAutoridad() != null) {
            AutoridadEntity autoridad = new AutoridadEntity();
            autoridad.setId(domain.getIdAutoridad().longValue());
            entity.setAutoridad(autoridad);
        }

        entity.setNumOficio(domain.getNumOficio());
        entity.setFechaAcuerdo(domain.getFechaAcuerdo());
        entity.setFundamento(domain.getFundamento());
        entity.setInicialesAsesor(domain.getInicialesAsesor());
        entity.setRutaPdf(domain.getRutaPdf());
        entity.setFechaGeneracion(domain.getFechaGeneracion());
        return entity;
    }

    public OficioNotificacion toDomain(OficioNotificacionEntity entity) {
        OficioNotificacion domain = new OficioNotificacion();
        domain.setId(entity.getId());

        domain.setFolioExpediente(entity.getFolioExpediente());

        if (entity.getAutoridad() != null) {
            domain.setIdAutoridad(entity.getAutoridad().getId().intValue());
            domain.setNombreAutoridad(entity.getAutoridad().getNombre());
        }

        domain.setNumOficio(entity.getNumOficio());
        domain.setFechaAcuerdo(entity.getFechaAcuerdo());
        domain.setFundamento(entity.getFundamento());
        domain.setInicialesAsesor(entity.getInicialesAsesor());
        domain.setRutaPdf(entity.getRutaPdf());
        domain.setFechaGeneracion(entity.getFechaGeneracion());
        return domain;
    }
}