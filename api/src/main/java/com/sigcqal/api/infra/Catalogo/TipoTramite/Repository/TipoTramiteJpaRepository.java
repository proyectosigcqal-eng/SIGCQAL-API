package com.sigcqal.api.infra.Catalogo.TipoTramite.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.Catalogo.TipoTramite.Entity.TipoTramiteEntity;

public interface TipoTramiteJpaRepository extends JpaRepository<TipoTramiteEntity, Long> {}
