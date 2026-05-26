package com.sigcqal.api.infra.Catalogo.TipoActoEmitido.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.Catalogo.TipoActoEmitido.Entity.TipoActoEmitidoEntity;

public interface TipoActoEmitidoJpaRepository extends JpaRepository<TipoActoEmitidoEntity, Long> {}
