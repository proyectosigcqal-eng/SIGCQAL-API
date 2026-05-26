package com.sigcqal.api.infra.Catalogo.CatAutoridad.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.Catalogo.CatAutoridad.Entity.CatAutoridadEntity;

public interface CatAutoridadJpaRepository extends JpaRepository<CatAutoridadEntity, Long> {}
