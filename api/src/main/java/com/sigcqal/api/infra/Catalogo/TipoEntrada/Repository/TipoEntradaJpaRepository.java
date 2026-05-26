package com.sigcqal.api.infra.Catalogo.TipoEntrada.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.Catalogo.TipoEntrada.Entity.TipoEntradaEntity;

public interface TipoEntradaJpaRepository extends JpaRepository<TipoEntradaEntity, Long> {}
