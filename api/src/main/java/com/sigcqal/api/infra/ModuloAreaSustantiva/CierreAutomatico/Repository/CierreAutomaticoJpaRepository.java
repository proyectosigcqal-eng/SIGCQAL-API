package com.sigcqal.api.infra.ModuloAreaSustantiva.CierreAutomatico.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;

public interface CierreAutomaticoJpaRepository extends JpaRepository<ExpedienteEntity, Integer> {

    // Trae todos los expedientes "En Prevención" no bloqueados
   // CierreAutomaticoJpaRepository — query corregido
@Query(value = """
    SELECT e.id_expediente,
           e.folio_gobierno,
           e.fecha_solicitud
    FROM sustantiva.expedientes e
    JOIN catalogos.estatus_expediente ee
      ON ee.id_estatus_expediente = e.id_estatus_expediente
    LEFT JOIN sustantiva.quejas qj
      ON qj.id_expediente = e.id_expediente
    WHERE UPPER(ee.nombre) LIKE '%PREVENCI%'
      AND (e.bloqueado IS NULL OR e.bloqueado = false)
      AND (
          qj.id_estatus_queja IS NULL        -- sin queja registrada
          OR qj.id_estatus_queja <= 2        -- solo Asignada(1) o Validación(2)
      )
    """, nativeQuery = true)
List<Object[]> findExpedientesEnPrevencionRaw();
    // Actualiza estatus y bloquea el expediente atómicamente
    @Modifying
    @Query(value = """
        UPDATE sustantiva.expedientes
        SET id_estatus_expediente = (
                SELECT id_estatus_expediente
                FROM catalogos.estatus_expediente
                WHERE UPPER(nombre) LIKE '%NO PRESENTADA%'
                LIMIT 1
            ),
            bloqueado = true,
            fecha_cierre_automatico = :ahora
        WHERE id_expediente = :idExpediente
        """, nativeQuery = true)
    void cerrarExpediente(
            @Param("idExpediente") Integer idExpediente,
            @Param("ahora") LocalDateTime ahora);
}
