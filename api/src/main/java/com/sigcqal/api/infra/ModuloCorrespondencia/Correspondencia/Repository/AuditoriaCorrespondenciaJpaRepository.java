package com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Entity.AuditoriaCorrespondenciaEntity;

@Repository
public interface AuditoriaCorrespondenciaJpaRepository extends JpaRepository<AuditoriaCorrespondenciaEntity, Long> {}
