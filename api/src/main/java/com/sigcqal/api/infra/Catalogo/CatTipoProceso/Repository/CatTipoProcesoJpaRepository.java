package com.sigcqal.api.infra.Catalogo.CatTipoProceso.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.Catalogo.CatTipoProceso.Entity.CatTipoProcesoEntity;

public interface CatTipoProcesoJpaRepository extends JpaRepository<CatTipoProcesoEntity, Long> {}
