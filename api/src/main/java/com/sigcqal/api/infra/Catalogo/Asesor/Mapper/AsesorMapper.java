package com.sigcqal.api.infra.Catalogo.Asesor.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Asesor.Model.Asesor;
import com.sigcqal.api.infra.Catalogo.Asesor.Entity.AsesorEntity;

@Component
public class AsesorMapper {
    public Asesor toDomain(AsesorEntity entity) {
        if (entity == null) {
            return null;
        }

        Asesor domain = new Asesor();
        domain.setIdAsesor(entity.getIdAsesor());
        domain.setIdPersona(entity.getIdPersona());
        domain.setEspecialidad(entity.getEspecialidad());
        domain.setCargaActual(entity.getCargaActual());
        domain.setUltimaAsignacionAt(entity.getUltimaAsignacionAt());
        domain.setActivo(entity.getActivo() != null ? entity.getActivo() : true);
        return domain;
    }

    public AsesorEntity toEntity(Asesor domain) {
        if (domain == null) {
            return null;
        }

        AsesorEntity entity = new AsesorEntity();
        entity.setIdAsesor(domain.getIdAsesor());
        entity.setIdPersona(domain.getIdPersona());
        entity.setEspecialidad(domain.getEspecialidad());
        entity.setCargaActual(domain.getCargaActual());
        entity.setUltimaAsignacionAt(domain.getUltimaAsignacionAt());
        entity.setActivo(domain.getActivo());
        return entity;
    }
}
