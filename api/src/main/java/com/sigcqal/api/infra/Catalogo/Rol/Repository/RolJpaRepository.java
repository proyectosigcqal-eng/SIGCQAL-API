package com.sigcqal.api.infra.Catalogo.Rol.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.Catalogo.Rol.Entity.RolEntity;

public interface RolJpaRepository extends JpaRepository<RolEntity, Long> {
    Optional<RolEntity> findByNombre(String nombre);
}
