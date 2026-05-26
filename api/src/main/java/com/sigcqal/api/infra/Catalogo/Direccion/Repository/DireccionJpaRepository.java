package com.sigcqal.api.infra.Catalogo.Direccion.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.Catalogo.Direccion.Entity.DireccionEntity;

public interface DireccionJpaRepository extends JpaRepository<DireccionEntity, Long> {}
