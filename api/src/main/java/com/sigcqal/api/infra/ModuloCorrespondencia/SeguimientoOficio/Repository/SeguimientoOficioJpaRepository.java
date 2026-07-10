package com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoOficio.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoOficio.Entity.SeguimientoOficioEntity;

public interface SeguimientoOficioJpaRepository extends JpaRepository<SeguimientoOficioEntity, Integer> {

    @Query("""
        SELECT s FROM SeguimientoOficioEntity s
        LEFT JOIN FETCH s.oficio
        LEFT JOIN FETCH s.usuario
        LEFT JOIN FETCH s.estatus
        """)
    List<SeguimientoOficioEntity> findAllConRelaciones();

    @Query("""
        SELECT s FROM SeguimientoOficioEntity s
        LEFT JOIN FETCH s.oficio
        LEFT JOIN FETCH s.usuario
        LEFT JOIN FETCH s.estatus
        WHERE s.idSeguimientoOficio = :id
        """)
    Optional<SeguimientoOficioEntity> findByIdConRelaciones(@Param("id") Integer id);

    @Query("""
        SELECT s FROM SeguimientoOficioEntity s
        LEFT JOIN FETCH s.oficio
        LEFT JOIN FETCH s.usuario
        LEFT JOIN FETCH s.estatus
        WHERE s.oficio.id = :idOficio
        """)
    List<SeguimientoOficioEntity> findByOficio_IdConRelaciones(@Param("idOficio") Long idOficio);

    @Query("SELECT s FROM SeguimientoOficioEntity s WHERE s.oficio.id = :idOficio")
    List<SeguimientoOficioEntity> findByOficio_IdOficio(@Param("idOficio") Long idOficio);
}
