package com.sigcqal.api.infra.ModuloAreaSustantiva.SentenciaDictada.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.ModuloAreaSustantiva.SentenciaDictada.Entity.SentenciaDictadaEntity;

public interface SentenciaDictadaJpaRepository extends JpaRepository<SentenciaDictadaEntity, Integer> {
}
