package com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Entity.AreaRefEntity;

@Repository
public interface AreaRefJpaRepository extends JpaRepository<AreaRefEntity, Long> {}

