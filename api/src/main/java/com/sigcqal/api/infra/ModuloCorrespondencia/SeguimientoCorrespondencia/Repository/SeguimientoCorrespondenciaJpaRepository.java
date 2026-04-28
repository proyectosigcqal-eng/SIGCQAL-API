package com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoCorrespondencia.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoCorrespondencia.Entity.SeguimientoCorrespondenciaEntity;

public interface SeguimientoCorrespondenciaJpaRepository extends JpaRepository<SeguimientoCorrespondenciaEntity, Integer> {

    @Query("SELECT s FROM SeguimientoCorrespondenciaEntity s WHERE s.correspondencia.id = :idCorrespondencia")
    List<SeguimientoCorrespondenciaEntity> findByCorrespondencia_IdCorrespondencia(
            @Param("idCorrespondencia") Long idCorrespondencia);
}
