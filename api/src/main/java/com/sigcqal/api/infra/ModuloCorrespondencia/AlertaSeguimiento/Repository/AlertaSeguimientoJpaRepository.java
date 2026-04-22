package com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Entity.AlertaSeguimientoEntity;

@Repository
public interface AlertaSeguimientoJpaRepository extends JpaRepository<AlertaSeguimientoEntity, Long> {
    @Query("""
        SELECT a
        FROM AlertaSeguimientoEntity a
        JOIN a.turno t
        WHERE t.idFolio = :idFolio
        ORDER BY a.fechaAlerta DESC
      """)
    List<AlertaSeguimientoEntity> findByIdFolioOrderByFechaAlertaDesc(@Param("idFolio") Long idFolio);
}

