package com.sigcqal.api.infra.Catalogo.Empleado.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Empleado.Model.Empleado;
import com.sigcqal.api.infra.Catalogo.Empleado.Entity.EmpleadoEntity;

@Component
public class EmpleadoMapper {
    public Empleado toDomain(EmpleadoEntity entity) {
        if (entity == null) return null;

        Empleado domain = new Empleado();
        domain.setId(entity.getId());
        domain.setNombreCompleto(entity.getNombreCompleto());
        domain.setCargo(entity.getCargo());
        domain.setIdArea(entity.getIdArea());
        return domain;
    }

    public EmpleadoEntity toEntity(Empleado domain) {
        if (domain == null) return null;

        EmpleadoEntity entity = new EmpleadoEntity();
        entity.setId(domain.getId());
        entity.setNombreCompleto(domain.getNombreCompleto());
        entity.setCargo(domain.getCargo());
        entity.setIdArea(domain.getIdArea());
        return entity;
    }
}
