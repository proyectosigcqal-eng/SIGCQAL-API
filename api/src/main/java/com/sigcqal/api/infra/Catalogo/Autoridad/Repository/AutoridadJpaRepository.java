package com.sigcqal.api.infra.Catalogo.Autoridad.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.Catalogo.Autoridad.Entity.AutoridadEntity;

public interface AutoridadJpaRepository extends JpaRepository<AutoridadEntity, Long> {}
