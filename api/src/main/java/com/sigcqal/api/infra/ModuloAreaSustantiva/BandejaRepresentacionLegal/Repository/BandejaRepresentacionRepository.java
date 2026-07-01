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
            e.folio_gobierno,                                                              -- row[0]
            e.id_expediente,                                                               -- row[1]
            m.nombre_municipio,                                                            -- row[2]
            CONCAT(p.nombre, ' ', p.apellido_paterno, ' ', COALESCE(p.apellido_materno, '')), -- row[3]
            ta.nombre,                                                                     -- row[4]
            COALESCE(erl.nombre_estatus, 'Asignado') AS descripcion_estatus,               -- row[5]
            CASE WHEN rl.es_evolucion = true THEN 'Evolución' ELSE 'Directo' END,           -- row[6]
            COALESCE(da.seguimiento, 'Representación Legal'),                             -- row[7]
            COALESCE(rl.fecha_creacion, da.fecha_notificacion::timestamp),                 -- row[8]
            COALESCE(e.bloqueado, false) AS bloqueado,                                   -- row[9]
            true AS tiene_ficha,                                                           -- row[10]
            EXISTS (
                SELECT 1 FROM sustantiva.rl_cir c
                WHERE c.id_expediente = e.id_expediente
            ) AS tiene_cir,                                                                -- row[11]
            EXISTS (
                SELECT 1 FROM sustantiva.irl_demanda_amparo d
                WHERE d.id_expediente = e.id_expediente
                  AND d.fecha_presentacion_demanda IS NOT NULL
            ) AS tiene_demanda,                                                            -- row[12]
            EXISTS (
                SELECT 1 FROM sustantiva.oficio_notificacion on2
                WHERE on2.folio_expediente = e.folio_gobierno
            ) AS tiene_oficio,                                                             -- row[13]
            EXISTS (
                SELECT 1
                FROM sustantiva.irl_demanda_amparo d
                JOIN sustantiva.audiencia_espera ae ON ae.id_demanda_amparo = d.id_demanda_amparo
                JOIN sustantiva.audiencia_celebrada ac ON ac.id_audiencia_espera = ae.id_audiencia_espera
                WHERE d.id_expediente = e.id_expediente
            ) AS tiene_audiencia,                                                          -- row[14]
            EXISTS (
                SELECT 1
                FROM sustantiva.irl_demanda_amparo d
                JOIN sustantiva.audiencia_espera ae ON ae.id_demanda_amparo = d.id_demanda_amparo
                JOIN sustantiva.audiencia_celebrada ac ON ac.id_audiencia_espera = ae.id_audiencia_espera
                JOIN sustantiva.sentencia_dictada sd ON sd.id_audiencia_celebrada = ac.id_audiencia_celebrada
                WHERE d.id_expediente = e.id_expediente
            ) AS tiene_sentencia,                                                          -- row[15]
            EXISTS (
                SELECT 1
                FROM sustantiva.irl_demanda_amparo d
                JOIN sustantiva.audiencia_espera ae ON ae.id_demanda_amparo = d.id_demanda_amparo
                JOIN sustantiva.audiencia_celebrada ac ON ac.id_audiencia_espera = ae.id_audiencia_espera
                JOIN sustantiva.sentencia_dictada sd ON sd.id_audiencia_celebrada = ac.id_audiencia_celebrada
                JOIN sustantiva.sentencia_ejecutoria se ON se.id_sentencia = sd.id_sentencia
                WHERE d.id_expediente = e.id_expediente
            ) AS tiene_ejecutoria,                                                         -- row[16]
            EXISTS (
                SELECT 1
                FROM sustantiva.irl_demanda_amparo d
                JOIN sustantiva.audiencia_espera ae ON ae.id_demanda_amparo = d.id_demanda_amparo
                JOIN sustantiva.audiencia_celebrada ac ON ac.id_audiencia_espera = ae.id_audiencia_espera
                JOIN sustantiva.sentencia_dictada sd ON sd.id_audiencia_celebrada = ac.id_audiencia_celebrada
                JOIN sustantiva.sentencia_ejecutoria se ON se.id_sentencia = sd.id_sentencia
                JOIN sustantiva.notificacion_sentencia_cumplida nc
                     ON nc.id_sentencia_ejecutoria = se.id_sentencia_ejecutoria
                WHERE d.id_expediente = e.id_expediente
            ) AS tiene_cumplimiento,                                                       -- row[17]
            (SELECT c.fecha_emision
               FROM sustantiva.rl_cir c
               WHERE c.id_expediente = e.id_expediente
               ORDER BY c.id_rl_cir DESC LIMIT 1) AS fecha_cir,                            -- row[18]
            (SELECT d.fecha_presentacion_demanda
               FROM sustantiva.irl_demanda_amparo d
               WHERE d.id_expediente = e.id_expediente
               ORDER BY d.id_demanda_amparo DESC LIMIT 1) AS fecha_demanda,                 -- row[19]
            (SELECT o.fecha_generacion
               FROM sustantiva.oficio_notificacion o
               WHERE o.folio_expediente = e.folio_gobierno
               ORDER BY o.id_oficio_notificacion DESC LIMIT 1) AS fecha_oficio,             -- row[20]
            (SELECT ac.fecha_hora_celebracion
               FROM sustantiva.irl_demanda_amparo d
               JOIN sustantiva.audiencia_espera ae ON ae.id_demanda_amparo = d.id_demanda_amparo
               JOIN sustantiva.audiencia_celebrada ac ON ac.id_audiencia_espera = ae.id_audiencia_espera
               WHERE d.id_expediente = e.id_expediente
               ORDER BY ac.id_audiencia_celebrada DESC LIMIT 1) AS fecha_audiencia,         -- row[21]
            (SELECT sd.fecha_dictado
               FROM sustantiva.irl_demanda_amparo d
               JOIN sustantiva.audiencia_espera ae ON ae.id_demanda_amparo = d.id_demanda_amparo
               JOIN sustantiva.audiencia_celebrada ac ON ac.id_audiencia_espera = ae.id_audiencia_espera
               JOIN sustantiva.sentencia_dictada sd ON sd.id_audiencia_celebrada = ac.id_audiencia_celebrada
               WHERE d.id_expediente = e.id_expediente
               ORDER BY sd.id_sentencia DESC LIMIT 1) AS fecha_sentencia,                  -- row[22]
            (SELECT se.fecha_declaracion_ejecutoria
               FROM sustantiva.irl_demanda_amparo d
               JOIN sustantiva.audiencia_espera ae ON ae.id_demanda_amparo = d.id_demanda_amparo
               JOIN sustantiva.audiencia_celebrada ac ON ac.id_audiencia_espera = ae.id_audiencia_espera
               JOIN sustantiva.sentencia_dictada sd ON sd.id_audiencia_celebrada = ac.id_audiencia_celebrada
               JOIN sustantiva.sentencia_ejecutoria se ON se.id_sentencia = sd.id_sentencia
               WHERE d.id_expediente = e.id_expediente
               ORDER BY se.id_sentencia_ejecutoria DESC LIMIT 1) AS fecha_ejecutoria         -- row[23]
        FROM sustantiva.expedientes e
        LEFT JOIN sustantiva.contribuyentes c ON c.id_contribuyentes = e.id_contribuyente
        LEFT JOIN catalogos.personas p ON p.id_persona = c.id_persona
        LEFT JOIN sustantiva.detalle_asesoria da ON da.id_expediente = e.id_expediente
        LEFT JOIN catalogos.cat_municipios m ON m.id_municipio = e.id_municipio
        LEFT JOIN catalogos.tipo_acto_emitido ta ON ta.id_tipo_acto_emitido = da.id_tipo_acto_emitido
        LEFT JOIN sustantiva.representacion_legal rl ON rl.id_expediente = e.id_expediente
        LEFT JOIN catalogos.estatus_representacion_legal erl ON erl.id_estatus = rl.id_estatus
        WHERE (:search IS NULL OR :search = ''
               OR LOWER(CONCAT(p.nombre, ' ', p.apellido_paterno)) LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(e.folio_gobierno) LIKE LOWER(CONCAT('%', :search, '%')))
          AND (
              :estatus IS NULL
              OR :estatus = ''
              OR erl.nombre_estatus = :estatus
              OR (:estatus = 'Asignado' AND rl.id_estatus IS NULL AND COALESCE(e.bloqueado, false) = false)
              OR (:estatus = 'Concluido' AND COALESCE(e.bloqueado, false) = true)
          )
          AND (:tipoTramite IS NULL OR :tipoTramite = ''
               OR CAST(e.id_tipo_tramite AS VARCHAR) = :tipoTramite)
        ORDER BY rl.fecha_creacion DESC NULLS LAST
        """, nativeQuery = true)
    List<Object[]> obtenerBandejaRaw(
        @Param("search") String search,
        @Param("estatus") String estatus,
        @Param("tipoTramite") String tipoTramite
    );
}
