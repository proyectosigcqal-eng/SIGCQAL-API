package com.sigcqal.api.infra.ModuloAreaSustantiva.DemandaAmparo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.ModuloAreaSustantiva.DemandaAmparo.Entity.DemandaAmparoEntity;

public interface DemandaAmparoJpaRepository extends JpaRepository<DemandaAmparoEntity, Integer> {
}
