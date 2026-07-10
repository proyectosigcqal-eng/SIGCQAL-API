package com.sigcqal.api.infra.ModuloCorrespondencia.AcuseOficio.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseOficio.Entity.AcuseOficioEntity;

public interface AcuseOficioJpaRepository extends JpaRepository<AcuseOficioEntity, Long> {

    List<AcuseOficioEntity> findByEsDelAreaTrueAndOficio_Area_Id(Long idArea);

    boolean existsByOficio_Id(Long idOficio);

    List<AcuseOficioEntity> findByOficio_Id(Long idOficio);

    @Query("""
        SELECT a FROM AcuseOficioEntity a
        LEFT JOIN FETCH a.oficio o
        LEFT JOIN FETCH o.correspondencia
        LEFT JOIN FETCH o.area
        LEFT JOIN FETCH a.usuarioRevisor
        """)
    List<AcuseOficioEntity> findAllConRelaciones();

    @Query("""
        SELECT a FROM AcuseOficioEntity a
        LEFT JOIN FETCH a.oficio o
        LEFT JOIN FETCH o.correspondencia
        LEFT JOIN FETCH o.area
        LEFT JOIN FETCH a.usuarioRevisor
        WHERE a.idAcuseOficio = :id
        """)
    Optional<AcuseOficioEntity> findByIdConRelaciones(@Param("id") Long id);

    @Query("""
        SELECT a FROM AcuseOficioEntity a
        LEFT JOIN FETCH a.oficio o
        LEFT JOIN FETCH o.correspondencia
        LEFT JOIN FETCH o.area
        LEFT JOIN FETCH a.usuarioRevisor
        WHERE a.esDelArea = true AND o.area.id = :idArea
        """)
    List<AcuseOficioEntity> findByEsDelAreaTrueAndOficio_Area_IdConRelaciones(
            @Param("idArea") Long idArea);

    @Query("""
        SELECT a FROM AcuseOficioEntity a
        LEFT JOIN FETCH a.oficio o
        LEFT JOIN FETCH o.correspondencia
        LEFT JOIN FETCH o.area
        LEFT JOIN FETCH a.usuarioRevisor
        WHERE o.id = :idOficio
        """)
    List<AcuseOficioEntity> findByOficio_IdConRelaciones(@Param("idOficio") Long idOficio);
}
