package com.sigcqal.api.infra.Catalogo.UsuarioRol.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.Catalogo.UsuarioRol.Entity.UsuarioRolEntity;

@Repository
public interface UsuarioRolRepository extends JpaRepository<UsuarioRolEntity, Long> {
    List<UsuarioRolEntity> findByUsuario_Id(Long usuarioId);
    List<UsuarioRolEntity> findByRol_Id(Long rolId);
}
