package com.sigcqal.api.infra.Catalogo.Rol.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.Catalogo.Rol.Entity.RolEntity;

public interface RolJpaRepository extends JpaRepository<RolEntity, Long> {}
