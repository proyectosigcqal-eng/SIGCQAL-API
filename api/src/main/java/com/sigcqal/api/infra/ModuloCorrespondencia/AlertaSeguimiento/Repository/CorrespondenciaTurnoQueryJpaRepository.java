package com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Entity.CorrespondenciaTurnoRefEntity;

@Repository
public interface CorrespondenciaTurnoQueryJpaRepository extends JpaRepository<CorrespondenciaTurnoRefEntity, Long> {
    Optional<CorrespondenciaTurnoRefEntity> findTopByIdFolioOrderByIdTurnoDesc(Long idFolio);
}

