package com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoOficio.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoOficio.Entity.SeguimientoOficioEntity;

public interface SeguimientoOficioJpaRepository extends JpaRepository<SeguimientoOficioEntity, Integer> {

    @Query("SELECT s FROM SeguimientoOficioEntity s WHERE s.oficio.id = :idOficio")
    List<SeguimientoOficioEntity> findByOficio_IdOficio(@Param("idOficio") Long idOficio);
}