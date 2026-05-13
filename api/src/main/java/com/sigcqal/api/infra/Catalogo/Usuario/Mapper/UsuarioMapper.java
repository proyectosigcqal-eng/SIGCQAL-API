package com.sigcqal.api.infra.Catalogo.Usuario.Mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Usuario.Model.Usuario;
import com.sigcqal.api.infra.Catalogo.Area.Entity.AreaEntity;
import com.sigcqal.api.infra.Catalogo.Rol.Entity.RolEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import com.sigcqal.api.infra.Catalogo.UsuarioRol.Entity.UsuarioRolEntity;

@Component
public class UsuarioMapper {

    public Usuario toDomain(UsuarioEntity entity) {
        if (entity == null) return null;

        Usuario domain = new Usuario();
        domain.setId(entity.getId());
        domain.setIdPersona(entity.getIdPersona());
        
        domain.setUsuarioLogin(entity.getUsuarioLogin());
        domain.setCorreoElectronico(entity.getCorreoElectronico());
        domain.setPassword(entity.getPassword());

         if (entity.getIdArea() != null) {
            domain.setIdArea(entity.getIdArea().getId());
            domain.setNombreArea(entity.getIdArea().getNombre());
        }

        if (entity.getUsuarioRoles() != null) {
            List<Long> ids = entity.getUsuarioRoles().stream()
                    .filter(ur -> ur.getRol() != null)
                    .map(ur -> ur.getRol().getId())
                    .collect(Collectors.toList());
            domain.setIdRoles(ids);
        }

        return domain;
    }

    public UsuarioEntity toEntity(Usuario domain) {
        if (domain == null) return null;

        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(domain.getId());
        entity.setIdPersona(domain.getIdPersona());
        entity.setUsuarioLogin(domain.getUsuarioLogin());
        entity.setCorreoElectronico(domain.getCorreoElectronico());
        entity.setPassword(domain.getPassword());

        if (domain.getIdArea() != null) {
            AreaEntity area = new AreaEntity();
            area.setId(domain.getIdArea());
            entity.setIdArea(area);
        }

        if (domain.getIdRoles() != null) {
            Set<UsuarioRolEntity> usuarioRoles = domain.getIdRoles().stream().map(idRol -> {
                UsuarioRolEntity ur = new UsuarioRolEntity();
                RolEntity rol = new RolEntity();
                rol.setId(idRol);
                ur.setRol(rol);
                ur.setUsuario(entity);
                ur.setFechaAsignacion(LocalDateTime.now());
                return ur;
            }).collect(Collectors.toSet());
            entity.setUsuarioRoles(usuarioRoles);
        }

        return entity;
    }
}
