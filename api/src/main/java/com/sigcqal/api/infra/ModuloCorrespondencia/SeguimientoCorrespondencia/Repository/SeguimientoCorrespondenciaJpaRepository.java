package com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoCorrespondencia.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoCorrespondencia.Entity.SeguimientoCorrespondenciaEntity;

public interface SeguimientoCorrespondenciaJpaRepository extends JpaRepository<SeguimientoCorrespondenciaEntity, Integer> {

    @Query("""
        SELECT s FROM SeguimientoCorrespondenciaEntity s
        LEFT JOIN FETCH s.correspondencia
        LEFT JOIN FETCH s.usuario
        LEFT JOIN FETCH s.estatus
        """)
    List<SeguimientoCorrespondenciaEntity> findAllConRelaciones();

    @Query("""
        SELECT s FROM SeguimientoCorrespondenciaEntity s
        LEFT JOIN FETCH s.correspondencia
        LEFT JOIN FETCH s.usuario
        LEFT JOIN FETCH s.estatus
        WHERE s.idSeguimientoCorrespondencia = :id
        """)
    Optional<SeguimientoCorrespondenciaEntity> findByIdConRelaciones(@Param("id") Integer id);

    @Query("""
        SELECT s FROM SeguimientoCorrespondenciaEntity s
        LEFT JOIN FETCH s.correspondencia
        LEFT JOIN FETCH s.usuario
        LEFT JOIN FETCH s.estatus
        WHERE s.correspondencia.id = :idCorrespondencia
        """)
    List<SeguimientoCorrespondenciaEntity> findByCorrespondencia_IdConRelaciones(
            @Param("idCorrespondencia") Long idCorrespondencia);

    @Query("SELECT s FROM SeguimientoCorrespondenciaEntity s WHERE s.correspondencia.id = :idCorrespondencia")
    List<SeguimientoCorrespondenciaEntity> findByCorrespondencia_IdCorrespondencia(
            @Param("idCorrespondencia") Long idCorrespondencia);
}
