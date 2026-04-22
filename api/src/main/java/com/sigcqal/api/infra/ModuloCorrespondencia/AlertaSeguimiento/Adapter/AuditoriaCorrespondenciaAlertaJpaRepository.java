package com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Entity.AuditoriaCorrespondenciaAlertaEntity;

@Repository
public interface AuditoriaCorrespondenciaAlertaJpaRepository extends JpaRepository<AuditoriaCorrespondenciaAlertaEntity, Long> {}