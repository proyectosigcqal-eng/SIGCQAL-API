package com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Entity.CorrespondenciaEntradaEntity;

@Repository
public interface CorrespondenciaEntradaJpaRepository extends JpaRepository<CorrespondenciaEntradaEntity, Long> {
    boolean existsByNumeroOficio(String numeroOficio);

    Optional<CorrespondenciaEntradaEntity> findTopByFolioUnicoEndingWithOrderByIdDesc(String suffix);
}
