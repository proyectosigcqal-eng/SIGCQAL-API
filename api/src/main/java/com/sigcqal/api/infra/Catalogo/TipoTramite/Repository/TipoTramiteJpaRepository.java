package com.sigcqal.api.infra.Catalogo.TipoTramite.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.Catalogo.TipoTramite.Entity.TipoTramiteEntity;

@Repository
public interface TipoTramiteJpaRepository extends JpaRepository<TipoTramiteEntity, Long> {}
