package com.sigcqal.api.infra.ModuloAreaSustantiva.BandejaRepresentacionLegal.Repository;
 
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
 
import java.util.List;
 
@Repository
public interface BandejaRepresentacionRepository extends JpaRepository<ExpedienteEntity, Integer> {
 
    @Query(value = """
        SELECT
            e.folio_gobierno,
            e.id_expediente,
            m.nombre_municipio,
            CONCAT(p.nombre, ' ', p.apellido_paterno, ' ', COALESCE(p.apellido_materno, '')),
            ta.nombre,
            CASE
                WHEN COALESCE(e.bloqueado, false) = true THEN 'Concluido'
                WHEN EXISTS (
                    SELECT 1 FROM sustantiva.irl_demanda_amparo d2
                    JOIN sustantiva.audiencia_espera ae2 ON ae2.id_demanda_amparo = d2.id_demanda_amparo
                    JOIN sustantiva.audiencia_celebrada ac2 ON ac2.id_audiencia_espera = ae2.id_audiencia_espera
                    JOIN sustantiva.sentencia_dictada sd2 ON sd2.id_audiencia_celebrada = ac2.id_audiencia_celebrada
                    JOIN sustantiva.sentencia_ejecutoria se2 ON se2.id_sentencia = sd2.id_sentencia
                    JOIN sustantiva.notificacion_sentencia_cumplida nc2 ON nc2.id_sentencia_ejecutoria = se2.id_sentencia_ejecutoria
                    WHERE d2.id_expediente = e.id_expediente
                ) THEN 'Cumplimiento notificado'
                WHEN EXISTS (
                    SELECT 1 FROM sustantiva.irl_demanda_amparo d2
                    JOIN sustantiva.audiencia_espera ae2 ON ae2.id_demanda_amparo = d2.id_demanda_amparo
                    JOIN sustantiva.audiencia_celebrada ac2 ON ac2.id_audiencia_espera = ae2.id_audiencia_espera
                    JOIN sustantiva.sentencia_dictada sd2 ON sd2.id_audiencia_celebrada = ac2.id_audiencia_celebrada
                    JOIN sustantiva.sentencia_ejecutoria se2 ON se2.id_sentencia = sd2.id_sentencia
                    WHERE d2.id_expediente = e.id_expediente
                ) THEN 'Sentencia Ejecutoria'
                WHEN EXISTS (
                    SELECT 1 FROM sustantiva.irl_demanda_amparo d2
                    JOIN sustantiva.audiencia_espera ae2 ON ae2.id_demanda_amparo = d2.id_demanda_amparo
                    JOIN sustantiva.audiencia_celebrada ac2 ON ac2.id_audiencia_espera = ae2.id_audiencia_espera
                    JOIN sustantiva.sentencia_dictada sd2 ON sd2.id_audiencia_celebrada = ac2.id_audiencia_celebrada
                    WHERE d2.id_expediente = e.id_expediente
                ) THEN 'Sentencia dictada'
                WHEN EXISTS (
                    SELECT 1 FROM sustantiva.irl_demanda_amparo d2
                    JOIN sustantiva.audiencia_espera ae2 ON ae2.id_demanda_amparo = d2.id_demanda_amparo
                    JOIN sustantiva.audiencia_celebrada ac2 ON ac2.id_audiencia_espera = ae2.id_audiencia_espera
                    WHERE d2.id_expediente = e.id_expediente
                ) THEN 'Audiencia celebrada'
                WHEN EXISTS (
                    SELECT 1 FROM sustantiva.irl_demanda_amparo d2
                    JOIN sustantiva.audiencia_espera ae2 ON ae2.id_demanda_amparo = d2.id_demanda_amparo
                    WHERE d2.id_expediente = e.id_expediente
                ) THEN 'Admitida en espera de audiencia'
                WHEN EXISTS (
                    SELECT 1 FROM sustantiva.irl_demanda_amparo d2
                    WHERE d2.id_expediente = e.id_expediente
                      AND d2.fecha_presentacion_demanda IS NOT NULL
                ) THEN 'Demanda presentada'
                WHEN EXISTS (
                    SELECT 1 FROM sustantiva.rl_cir c2
                    WHERE c2.id_expediente = e.id_expediente
                ) THEN 'CIR generado'
                ELSE 'Asignado'
            END AS descripcion_estatus,
            CASE WHEN rl.es_evolucion = true THEN 'Evolución' ELSE 'Directo' END,
            COALESCE(da.seguimiento, 'Representación Legal'),
            COALESCE(rl.fecha_creacion, da.fecha_notificacion::timestamp),
            COALESCE(e.bloqueado, false)                                                AS bloqueado,
            true                                                                         AS tiene_ficha,
            EXISTS (SELECT 1 FROM sustantiva.rl_cir c
                    WHERE c.id_expediente = e.id_expediente)                             AS tiene_cir,
            EXISTS (SELECT 1 FROM sustantiva.irl_demanda_amparo d
                    WHERE d.id_expediente = e.id_expediente
                      AND d.fecha_presentacion_demanda IS NOT NULL)                      AS tiene_demanda,
            EXISTS (SELECT 1 FROM sustantiva.oficio_notificacion on2
                    WHERE on2.folio_expediente = e.folio_gobierno)                       AS tiene_oficio,
            EXISTS (SELECT 1
                    FROM sustantiva.irl_demanda_amparo d
                    JOIN sustantiva.audiencia_espera ae ON ae.id_demanda_amparo = d.id_demanda_amparo
                    JOIN sustantiva.audiencia_celebrada ac ON ac.id_audiencia_espera = ae.id_audiencia_espera
                    WHERE d.id_expediente = e.id_expediente)                             AS tiene_audiencia,
            EXISTS (SELECT 1
                    FROM sustantiva.irl_demanda_amparo d
                    JOIN sustantiva.audiencia_espera ae ON ae.id_demanda_amparo = d.id_demanda_amparo
                    JOIN sustantiva.audiencia_celebrada ac ON ac.id_audiencia_espera = ae.id_audiencia_espera
                    JOIN sustantiva.sentencia_dictada sd ON sd.id_audiencia_celebrada = ac.id_audiencia_celebrada
                    WHERE d.id_expediente = e.id_expediente)                             AS tiene_sentencia,
            EXISTS (SELECT 1
                    FROM sustantiva.irl_demanda_amparo d
                    JOIN sustantiva.audiencia_espera ae ON ae.id_demanda_amparo = d.id_demanda_amparo
                    JOIN sustantiva.audiencia_celebrada ac ON ac.id_audiencia_espera = ae.id_audiencia_espera
                    JOIN sustantiva.sentencia_dictada sd ON sd.id_audiencia_celebrada = ac.id_audiencia_celebrada
                    JOIN sustantiva.sentencia_ejecutoria se ON se.id_sentencia = sd.id_sentencia
                    WHERE d.id_expediente = e.id_expediente)                             AS tiene_ejecutoria,
            EXISTS (SELECT 1
                    FROM sustantiva.irl_demanda_amparo d
                    JOIN sustantiva.audiencia_espera ae ON ae.id_demanda_amparo = d.id_demanda_amparo
                    JOIN sustantiva.audiencia_celebrada ac ON ac.id_audiencia_espera = ae.id_audiencia_espera
                    JOIN sustantiva.sentencia_dictada sd ON sd.id_audiencia_celebrada = ac.id_audiencia_celebrada
                    JOIN sustantiva.sentencia_ejecutoria se ON se.id_sentencia = sd.id_sentencia
                    JOIN sustantiva.notificacion_sentencia_cumplida nc
                         ON nc.id_sentencia_ejecutoria = se.id_sentencia_ejecutoria
                    WHERE d.id_expediente = e.id_expediente)                             AS tiene_cumplimiento,
            (SELECT c.fecha_emision FROM sustantiva.rl_cir c
               WHERE c.id_expediente = e.id_expediente
               ORDER BY c.id_rl_cir DESC LIMIT 1)                                       AS fecha_cir,
            (SELECT d.fecha_presentacion_demanda FROM sustantiva.irl_demanda_amparo d
               WHERE d.id_expediente = e.id_expediente
               ORDER BY d.id_demanda_amparo DESC LIMIT 1)                               AS fecha_demanda,
            (SELECT o.fecha_generacion FROM sustantiva.oficio_notificacion o
               WHERE o.folio_expediente = e.folio_gobierno
               ORDER BY o.id_oficio_notificacion DESC LIMIT 1)                          AS fecha_oficio,
            (SELECT ac.fecha_hora_celebracion
               FROM sustantiva.irl_demanda_amparo d
               JOIN sustantiva.audiencia_espera ae ON ae.id_demanda_amparo = d.id_demanda_amparo
               JOIN sustantiva.audiencia_celebrada ac ON ac.id_audiencia_espera = ae.id_audiencia_espera
               WHERE d.id_expediente = e.id_expediente
               ORDER BY ac.id_audiencia_celebrada DESC LIMIT 1)                         AS fecha_audiencia,
            (SELECT sd.fecha_dictado
               FROM sustantiva.irl_demanda_amparo d
               JOIN sustantiva.audiencia_espera ae ON ae.id_demanda_amparo = d.id_demanda_amparo
               JOIN sustantiva.audiencia_celebrada ac ON ac.id_audiencia_espera = ae.id_audiencia_espera
               JOIN sustantiva.sentencia_dictada sd ON sd.id_audiencia_celebrada = ac.id_audiencia_celebrada
               WHERE d.id_expediente = e.id_expediente
               ORDER BY sd.id_sentencia DESC LIMIT 1)                                   AS fecha_sentencia,
            (SELECT se.fecha_declaracion_ejecutoria
               FROM sustantiva.irl_demanda_amparo d
               JOIN sustantiva.audiencia_espera ae ON ae.id_demanda_amparo = d.id_demanda_amparo
               JOIN sustantiva.audiencia_celebrada ac ON ac.id_audiencia_espera = ae.id_audiencia_espera
               JOIN sustantiva.sentencia_dictada sd ON sd.id_audiencia_celebrada = ac.id_audiencia_celebrada
               JOIN sustantiva.sentencia_ejecutoria se ON se.id_sentencia = sd.id_sentencia
               WHERE d.id_expediente = e.id_expediente
               ORDER BY se.id_sentencia_ejecutoria DESC LIMIT 1)                        AS fecha_ejecutoria,
            (SELECT d.fecha_registro FROM sustantiva.irl_demanda_amparo d
               WHERE d.id_expediente = e.id_expediente
               ORDER BY d.id_demanda_amparo DESC LIMIT 1)                               AS fecha_registro,
            (SELECT d.id_demanda_amparo FROM sustantiva.irl_demanda_amparo d
               WHERE d.id_expediente = e.id_expediente
               ORDER BY d.id_demanda_amparo DESC LIMIT 1)                               AS id_demanda_amparo,
            CAST(NULL AS VARCHAR)                                                         AS semaforo,
 
            -- ★ Columnas nuevas [27-30]
            rl.id                                                                         AS id_representacion_legal,
            (SELECT c.id_rl_cir FROM sustantiva.rl_cir c
               WHERE c.id_expediente = e.id_expediente
               ORDER BY c.id_rl_cir DESC LIMIT 1)                                       AS id_rl_cir,
            (SELECT qc.id_queja_rl_cir
               FROM sustantiva.queja_rl_cir qc
               JOIN sustantiva.representacion_legal rl2 ON rl2.id_resolucion_final = qc.id_resolucion_final
               WHERE rl2.id_expediente = e.id_expediente
               ORDER BY qc.id_queja_rl_cir DESC LIMIT 1) AS id_queja_rl_cir,
            COALESCE(rl.es_evolucion, false)                                             AS es_evolucion
 
        FROM sustantiva.expedientes e
        LEFT JOIN sustantiva.contribuyentes c       ON c.id_contribuyentes = e.id_contribuyente
        LEFT JOIN catalogos.personas p              ON p.id_persona = c.id_persona
        LEFT JOIN sustantiva.detalle_asesoria da    ON da.id_expediente = e.id_expediente
        LEFT JOIN catalogos.cat_municipios m        ON m.id_municipio = e.id_municipio
        LEFT JOIN catalogos.tipo_acto_emitido ta    ON ta.id_tipo_acto_emitido = da.id_tipo_acto_emitido
        LEFT JOIN sustantiva.representacion_legal rl ON rl.id_expediente = e.id_expediente
        LEFT JOIN catalogos.estatus_representacion_legal erl ON erl.id_estatus = rl.id_estatus
        WHERE (:search IS NULL OR :search = ''
               OR LOWER(CONCAT(p.nombre, ' ', p.apellido_paterno)) LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(e.folio_gobierno) LIKE LOWER(CONCAT('%', :search, '%')))
          AND (
              :estatus IS NULL
              OR :estatus = ''
              OR (
                  CASE
                      WHEN COALESCE(e.bloqueado, false) = true THEN 'Concluido'
                      WHEN EXISTS (
                          SELECT 1 FROM sustantiva.irl_demanda_amparo d2
                          JOIN sustantiva.audiencia_espera ae2 ON ae2.id_demanda_amparo = d2.id_demanda_amparo
                          JOIN sustantiva.audiencia_celebrada ac2 ON ac2.id_audiencia_espera = ae2.id_audiencia_espera
                          JOIN sustantiva.sentencia_dictada sd2 ON sd2.id_audiencia_celebrada = ac2.id_audiencia_celebrada
                          JOIN sustantiva.sentencia_ejecutoria se2 ON se2.id_sentencia = sd2.id_sentencia
                          JOIN sustantiva.notificacion_sentencia_cumplida nc2 ON nc2.id_sentencia_ejecutoria = se2.id_sentencia_ejecutoria
                          WHERE d2.id_expediente = e.id_expediente
                      ) THEN 'Cumplimiento notificado'
                      WHEN EXISTS (
                          SELECT 1 FROM sustantiva.irl_demanda_amparo d2
                          JOIN sustantiva.audiencia_espera ae2 ON ae2.id_demanda_amparo = d2.id_demanda_amparo
                          JOIN sustantiva.audiencia_celebrada ac2 ON ac2.id_audiencia_espera = ae2.id_audiencia_espera
                          JOIN sustantiva.sentencia_dictada sd2 ON sd2.id_audiencia_celebrada = ac2.id_audiencia_celebrada
                          JOIN sustantiva.sentencia_ejecutoria se2 ON se2.id_sentencia = sd2.id_sentencia
                          WHERE d2.id_expediente = e.id_expediente
                      ) THEN 'Sentencia Ejecutoria'
                      WHEN EXISTS (
                          SELECT 1 FROM sustantiva.irl_demanda_amparo d2
                          JOIN sustantiva.audiencia_espera ae2 ON ae2.id_demanda_amparo = d2.id_demanda_amparo
                          JOIN sustantiva.audiencia_celebrada ac2 ON ac2.id_audiencia_espera = ae2.id_audiencia_espera
                          JOIN sustantiva.sentencia_dictada sd2 ON sd2.id_audiencia_celebrada = ac2.id_audiencia_celebrada
                          WHERE d2.id_expediente = e.id_expediente
                      ) THEN 'Sentencia dictada'
                      WHEN EXISTS (
                          SELECT 1 FROM sustantiva.irl_demanda_amparo d2
                          JOIN sustantiva.audiencia_espera ae2 ON ae2.id_demanda_amparo = d2.id_demanda_amparo
                          JOIN sustantiva.audiencia_celebrada ac2 ON ac2.id_audiencia_espera = ae2.id_audiencia_espera
                          WHERE d2.id_expediente = e.id_expediente
                      ) THEN 'Audiencia celebrada'
                      WHEN EXISTS (
                          SELECT 1 FROM sustantiva.irl_demanda_amparo d2
                          JOIN sustantiva.audiencia_espera ae2 ON ae2.id_demanda_amparo = d2.id_demanda_amparo
                          WHERE d2.id_expediente = e.id_expediente
                      ) THEN 'Admitida en espera de audiencia'
                      WHEN EXISTS (
                          SELECT 1 FROM sustantiva.irl_demanda_amparo d2
                          WHERE d2.id_expediente = e.id_expediente
                            AND d2.fecha_presentacion_demanda IS NOT NULL
                      ) THEN 'Demanda presentada'
                      WHEN EXISTS (
                          SELECT 1 FROM sustantiva.rl_cir c2
                          WHERE c2.id_expediente = e.id_expediente
                      ) THEN 'CIR generado'
                      ELSE 'Asignado'
                  END
              ) = :estatus
          )
          AND (:tipoTramite IS NULL OR :tipoTramite = ''
               OR CAST(e.id_tipo_tramite AS VARCHAR) = :tipoTramite)
          -- ★ NUEVO: filtro por es_evolucion — null = sin filtro (muestra todos)
          AND (:esEvolucion IS NULL OR rl.es_evolucion = :esEvolucion)
        ORDER BY rl.fecha_creacion DESC NULLS LAST
        """, nativeQuery = true)
    List<Object[]> obtenerBandejaRaw(
        @Param("search")      String  search,
        @Param("estatus")     String  estatus,
        @Param("tipoTramite") String  tipoTramite,
        @Param("esEvolucion") Boolean esEvolucion   // ← NUEVO
    );
}