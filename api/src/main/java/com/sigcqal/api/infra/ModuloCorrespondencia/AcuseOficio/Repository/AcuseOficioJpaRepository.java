package com.sigcqal.api.infra.ModuloCorrespondencia.AcuseOficio.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseOficio.Entity.AcuseOficioEntity;

public interface AcuseOficioJpaRepository extends JpaRepository<AcuseOficioEntity, Long> {

    List<AcuseOficioEntity> findByEsDelAreaTrueAndOficio_Area_Id(Long idArea);

    boolean existsByOficio_Id(Long idOficio);
    List<AcuseOficioEntity> findByOficio_Id(Long idOficio);

    // AcuseOficioJpaRepository.java
@Query("""
    SELECT a FROM AcuseOficioEntity a
    LEFT JOIN FETCH a.oficio o
    LEFT JOIN FETCH o.correspondencia
    LEFT JOIN FETCH o.area
""")
List<AcuseOficioEntity> findAllConRelaciones();
}