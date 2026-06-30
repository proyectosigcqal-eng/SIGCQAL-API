package com.sigcqal.api.infra.ModuloAreaSustantiva.RepresentacionLegal.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigcqal.api.infra.ModuloAreaSustantiva.RepresentacionLegal.Entity.RepresentacionLegalEntity;

public interface RepresentacionLegalJpaRepository
              extends JpaRepository<RepresentacionLegalEntity, Integer> {

       @Query(value = """
                     SELECT rl.id,
                            e.folio_gobierno,
                            COALESCE(pc.nombre || ' ' || pc.apellido_paterno || ' ' || pc.apellido_materno, ''),
                            COALESCE(pa.nombre || ' ' || pa.apellido_paterno || ' ' || pa.apellido_materno, ''),
                            COALESCE(m.nombre_municipio, ''),
                            COALESCE(erl.nombre_estatus, ''),
                            rl.fecha_creacion,
                            rl.es_evolucion,
                            rl.id_estatus
                     FROM sustantiva.representacion_legal rl
                     LEFT JOIN sustantiva.resolucion_final rf
                            ON rl.id_resolucion_final = rf.id_resolucion_final
                     LEFT JOIN sustantiva.expedientes e
                            ON e.id_expediente = COALESCE(rl.id_expediente, rf.id_expediente)
                     LEFT JOIN sustantiva.contribuyentes c  ON e.id_contribuyente = c.id_contribuyentes
                     LEFT JOIN catalogos.personas pc       ON c.id_persona = pc.id_persona
                     LEFT JOIN sustantiva.asesores a       ON e.id_asesor = a.id_asesores
                     LEFT JOIN catalogos.personas pa       ON a.id_persona = pa.id_persona
                     LEFT JOIN catalogos.cat_municipios m  ON e.id_municipio = m.id_municipio
                     LEFT JOIN catalogos.estatus_representacion_legal erl
                            ON rl.id_estatus = erl.id_estatus
                     WHERE rl.es_evolucion = ?1
                       AND (?2 IS NULL
                            OR e.folio_gobierno   ILIKE CONCAT('%', ?2, '%')
                            OR pc.nombre          ILIKE CONCAT('%', ?2, '%')
                            OR pc.apellido_paterno ILIKE CONCAT('%', ?2, '%')
                            OR pc.apellido_materno ILIKE CONCAT('%', ?2, '%'))
                       AND (?3 IS NULL OR rl.id_estatus = ?3)
                     ORDER BY rl.fecha_creacion DESC
                     """, nativeQuery = true)
       List<Object[]> findBandeja(
                     @Param("esEvolucion") Boolean esEvolucion,
                     @Param("search") String search,
                     @Param("idEstatus") Integer idEstatus);
}