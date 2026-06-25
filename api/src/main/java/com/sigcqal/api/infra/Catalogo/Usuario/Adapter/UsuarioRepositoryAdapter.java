package com.sigcqal.api.infra.Catalogo.Usuario.Adapter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sigcqal.api.domain.Catalogo.Usuario.Model.Usuario;
import com.sigcqal.api.domain.Catalogo.Usuario.Port.UsuarioRepositoryPort;
import com.sigcqal.api.infra.Catalogo.Rol.Entity.RolEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Mapper.UsuarioMapper;
import com.sigcqal.api.infra.Catalogo.Usuario.Repository.UsuarioJpaRepository;
import com.sigcqal.api.infra.Catalogo.UsuarioRol.Entity.UsuarioRolEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository jpaRepository;
    private final UsuarioMapper        mapper;

    @Override
    public Optional<Usuario> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Usuario> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity entity = mapper.toEntity(usuario);
        entity.setActivo(true);
        entity.setCreatedAt(LocalDateTime.now());
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    @Transactional
    public void actualizarRoles(Long idUsuario, List<Long> idRoles) {
        UsuarioEntity entity = jpaRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + idUsuario));

        // Limpia roles existentes y asigna los nuevos
        entity.getUsuarioRoles().clear();

        if (idRoles != null) {
            Set<UsuarioRolEntity> nuevosRoles = idRoles.stream().map(idRol -> {
                UsuarioRolEntity ur = new UsuarioRolEntity();
                RolEntity rol = new RolEntity();
                rol.setId(idRol);
                ur.setRol(rol);
                ur.setUsuario(entity);
                ur.setFechaAsignacion(LocalDateTime.now());
                return ur;
            }).collect(Collectors.toSet());

            entity.getUsuarioRoles().addAll(nuevosRoles);
        }

        jpaRepository.save(entity);
    }

    @Override
    @Transactional
    public void darBaja(Long idUsuario) {
        UsuarioEntity entity = jpaRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + idUsuario));
        entity.setActivo(false);
        jpaRepository.save(entity);
    }
}