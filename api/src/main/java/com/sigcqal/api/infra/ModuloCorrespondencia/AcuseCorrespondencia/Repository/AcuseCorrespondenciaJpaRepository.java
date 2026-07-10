package com.sigcqal.api.infra.ModuloCorrespondencia.AcuseCorrespondencia.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseCorrespondencia.Entity.AcuseCorrespondenciaEntity;

public interface AcuseCorrespondenciaJpaRepository
        extends JpaRepository<AcuseCorrespondenciaEntity, Long> {

    @Query("""
        SELECT a FROM AcuseCorrespondenciaEntity a
        LEFT JOIN FETCH a.correspondencia c
        LEFT JOIN FETCH c.area
        LEFT JOIN FETCH a.usuarioRevisor
        """)
    List<AcuseCorrespondenciaEntity> findAllConRelaciones();

    @Query("""
        SELECT a FROM AcuseCorrespondenciaEntity a
        LEFT JOIN FETCH a.correspondencia c
        LEFT JOIN FETCH c.area
        LEFT JOIN FETCH a.usuarioRevisor
        WHERE a.esDelArea = true AND c.area.id = :idArea
        """)
    List<AcuseCorrespondenciaEntity> findByEsDelAreaTrueAndCorrespondencia_Area_IdConRelaciones(
            @Param("idArea") Long idArea);

    @Query("""
        SELECT a FROM AcuseCorrespondenciaEntity a
        LEFT JOIN FETCH a.correspondencia c
        LEFT JOIN FETCH c.area
        LEFT JOIN FETCH a.usuarioRevisor
        WHERE c.id = :idCorrespondencia
        """)
    List<AcuseCorrespondenciaEntity> findByCorrespondencia_IdConRelaciones(
            @Param("idCorrespondencia") Long idCorrespondencia);

    List<AcuseCorrespondenciaEntity>
        findByEsDelAreaTrueAndCorrespondencia_Area_Id(Long idArea);

    List<AcuseCorrespondenciaEntity> findByCorrespondencia_Id(Long idCorrespondencia);
}
