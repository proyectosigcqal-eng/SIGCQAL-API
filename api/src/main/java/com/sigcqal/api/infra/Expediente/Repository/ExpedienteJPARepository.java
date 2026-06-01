package com.sigcqal.api.infra.Expediente.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.Expediente.Entity.ExpedienteEntity;

public interface ExpedienteJPARepository extends JpaRepository<ExpedienteEntity, Integer> {
}
