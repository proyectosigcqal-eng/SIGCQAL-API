package com.sigcqal.api.infra.Catalogo.Usuario.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Usuario.Model.Usuario;
import com.sigcqal.api.infra.Catalogo.Area.Entity.AreaEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;

@Component
public class UsuarioMapper {

    public Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) return null;

        Usuario domain = new Usuario();
        domain.setId(entity.getId());
        domain.setIdPersona(entity.getIdPersona());
        domain.setIdRol(entity.getIdRol());
        domain.setUsuarioLogin(entity.getUsuarioLogin());
        domain.setCorreoElectronico(entity.getCorreoElectronico());
        domain.setPassword(entity.getPassword());

         if (entity.getIdArea() != null) {
            domain.setIdArea(entity.getIdArea().getId());
            domain.setNombreArea(entity.getIdArea().getNombre());
        }
        return domain;
    }

    public UsuarioEntity toEntity(Usuario domain) {
        if (domain == null) return null;

        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(domain.getId());
        entity.setIdPersona(domain.getIdPersona());
        entity.setIdRol(domain.getIdRol());
        entity.setUsuarioLogin(domain.getUsuarioLogin());
        entity.setCorreoElectronico(domain.getCorreoElectronico());
        entity.setPassword(domain.getPassword());

        if (domain.getIdArea() != null) {
            AreaEntity area = new AreaEntity();
            area.setId(domain.getIdArea());
            entity.setIdArea(area);
            
        }
        
        return entity;
    }
}
