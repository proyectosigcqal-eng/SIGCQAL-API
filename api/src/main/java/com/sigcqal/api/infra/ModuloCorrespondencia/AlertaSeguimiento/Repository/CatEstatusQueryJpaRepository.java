package com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Entity.CatEstatusRefEntity;

@Repository
public interface CatEstatusQueryJpaRepository extends JpaRepository<CatEstatusRefEntity, Long> {
    Optional<CatEstatusRefEntity> findByNombreEstatusIgnoreCase(String nombreEstatus);
}

