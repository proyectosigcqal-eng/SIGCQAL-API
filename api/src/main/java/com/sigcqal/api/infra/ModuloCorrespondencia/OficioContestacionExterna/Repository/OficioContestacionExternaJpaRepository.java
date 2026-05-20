package com.sigcqal.api.infra.ModuloCorrespondencia.OficioContestacionExterna.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.ModuloCorrespondencia.OficioContestacionExterna.Entity.OficioContestacionExternaEntity;

public interface OficioContestacionExternaJpaRepository
        extends JpaRepository<OficioContestacionExternaEntity, Long> {

    Optional<OficioContestacionExternaEntity> findByCorrespondencia_Id(Long idCorrespondencia);
}
