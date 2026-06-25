package com.sigcqal.api.infra.ModuloAreaSustantiva.DiaInahabil.Repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ExpedientePrevencionJpaRepository
        extends JpaRepository<ExpedienteEntity, Integer> {


@Query(value = """
    SELECT e.fecha_solicitud
    FROM sustantiva.expedientes e
    WHERE e.folio_gobierno = :folio
    LIMIT 1
    """, nativeQuery = true)
Optional<LocalDateTime> findFechaPrevencionByFolio(@Param("folio") String folio);
@Query(value = """
    SELECT e.bloqueado FROM sustantiva.expedientes e
    WHERE e.folio_gobierno = :folio LIMIT 1
    """, nativeQuery = true)
Optional<Boolean> findBloqueadoByFolio(@Param("folio") String folio);

@Modifying
@Query(value = """
    UPDATE sustantiva.expedientes
    SET bloqueado = true,
        fecha_cierre_automatico = NOW(),
        id_estatus_expediente = 5
    WHERE folio_gobierno = :folio
      AND (bloqueado IS NULL OR bloqueado = false)
    """, nativeQuery = true)
void cerrarExpedienteVencido(@Param("folio") String folio);
        }