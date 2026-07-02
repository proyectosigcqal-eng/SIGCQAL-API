package com.sigcqal.api.infra.ModuloAreaSustantiva.BandejaRepresentacionLegal.Repository;

import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleIrlRepository extends JpaRepository<ExpedienteEntity, Integer> {

    @Query(value = """
        SELECT
            -- Audiencia Espera
            ae.numero_oficio_admision,           -- [0]
            ae.fecha_notificacion_oficio,         -- [1]
            ae.fecha_hora_audiencia_prog,         -- [2]
            ae.observaciones,                     -- [3]
            ae.ruta_pdf_oficio,                   -- [4]
            -- Audiencia Celebrada
            ac.fecha_hora_celebracion,            -- [5]
            ac.numero_oficio_acta,                -- [6]
            ac.sala_o_modalidad,                  -- [7]
            ac.resultado_audiencia,               -- [8]
            ac.asistio_autoridad,                 -- [9]
            ac.ruta_pdf_oficio,                   -- [10]
            -- Sentencia Dictada
            sd.fecha_dictado,                     -- [11]
            sd.fecha_notificacion_sentencia,      -- [12]
            sd.sentido_fallo,                     -- [13]
            sd.puntos_resolutivos,                -- [14]
            sd.numero_oficio_sentencia,           -- [15]
            sd.ruta_archivo_sentencia,            -- [16]
            sd.ruta_pdf_oficio,                   -- [17]
            -- Sentencia Ejecutoria
            se.numero_oficio_ejecutoria,          -- [18]
            se.fecha_declaracion_ejecutoria,      -- [19]
            se.requerimiento_cumplimiento,        -- [20]
            se.ruta_pdf_oficio,                   -- [21]
            -- Notificación Cumplida
            nc.numero_oficio_cumplimiento,        -- [22]
            nc.numero_oficio_archivo,             -- [23]
            nc.fecha_notificacion_archivo,        -- [24]
            nc.observaciones_finales,             -- [25]
            nc.ruta_pdf_oficio                    -- [26]
        FROM sustantiva.expedientes e
        JOIN sustantiva.irl_demanda_amparo d
            ON d.id_expediente = e.id_expediente
        LEFT JOIN sustantiva.audiencia_espera ae
            ON ae.id_demanda_amparo = d.id_demanda_amparo
        LEFT JOIN sustantiva.audiencia_celebrada ac
            ON ac.id_audiencia_espera = ae.id_audiencia_espera
        LEFT JOIN sustantiva.sentencia_dictada sd
            ON sd.id_audiencia_celebrada = ac.id_audiencia_celebrada
        LEFT JOIN sustantiva.sentencia_ejecutoria se
            ON se.id_sentencia = sd.id_sentencia
        LEFT JOIN sustantiva.notificacion_sentencia_cumplida nc
            ON nc.id_sentencia_ejecutoria = se.id_sentencia_ejecutoria
        WHERE e.folio_gobierno = :folio
        ORDER BY ae.id_audiencia_espera DESC
        LIMIT 1
        """, nativeQuery = true)
    List<Object[]> findDetalleByFolio(@Param("folio") String folio);
}