package com.sigcqal.api.infra.ModuloCorrespondencia.BitacoraHistorica.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigcqal.api.infra.ModuloCorrespondencia.BitacoraHistorica.Entity.BitacoraHistoricaEntity;

public interface BitacoraHistoricaJpaRepository extends JpaRepository<BitacoraHistoricaEntity, Long> {

    @Query("""
        SELECT b FROM BitacoraHistoricaEntity b
        LEFT JOIN FETCH b.correspondencia
        LEFT JOIN FETCH b.usuarioAccion
        LEFT JOIN FETCH b.estatusAnterior
        LEFT JOIN FETCH b.estatusNuevo
        WHERE b.correspondencia.id = :idCorrespondencia
        ORDER BY b.fechaMovimiento ASC
        """)
    List<BitacoraHistoricaEntity> findByCorrespondenciaIdConRelacionesOrderByFechaMovimientoAsc(
            @Param("idCorrespondencia") Long idCorrespondencia);

    List<BitacoraHistoricaEntity> findByCorrespondenciaIdOrderByFechaMovimientoAsc(Long idCorrespondencia);
}
