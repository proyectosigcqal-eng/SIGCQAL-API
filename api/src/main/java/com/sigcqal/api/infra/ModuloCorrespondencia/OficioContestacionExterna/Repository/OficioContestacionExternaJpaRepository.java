package com.sigcqal.api.infra.ModuloCorrespondencia.OficioContestacionExterna.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigcqal.api.infra.ModuloCorrespondencia.OficioContestacionExterna.Entity.OficioContestacionExternaEntity;

public interface OficioContestacionExternaJpaRepository
        extends JpaRepository<OficioContestacionExternaEntity, Long> {

    @Query("""
        SELECT o FROM OficioContestacionExternaEntity o
        LEFT JOIN FETCH o.correspondencia
        LEFT JOIN FETCH o.usuarioEmisor
        WHERE o.correspondencia.id = :idCorrespondencia
        """)
    Optional<OficioContestacionExternaEntity> findByCorrespondencia_IdConRelaciones(
            @Param("idCorrespondencia") Long idCorrespondencia);

    @Query("""
        SELECT o FROM OficioContestacionExternaEntity o
        LEFT JOIN FETCH o.correspondencia
        LEFT JOIN FETCH o.usuarioEmisor
        """)
    List<OficioContestacionExternaEntity> findAllConRelaciones();

    Optional<OficioContestacionExternaEntity> findByCorrespondencia_Id(Long idCorrespondencia);
}
