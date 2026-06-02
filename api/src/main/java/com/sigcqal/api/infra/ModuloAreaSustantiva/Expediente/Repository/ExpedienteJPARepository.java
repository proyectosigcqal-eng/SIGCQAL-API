package com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;

public interface ExpedienteJPARepository extends JpaRepository<ExpedienteEntity, Integer> {
}
