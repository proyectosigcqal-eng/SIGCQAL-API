package com.sigcqal.api.infra.ModuloAreaSustantiva.BandejaAsesoria.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import java.util.List;

@Repository
public interface BandejaAsesoriaRepository extends JpaRepository<ExpedienteEntity, Integer> {

  @Query(value = """
  SELECT 
    e.folio_gobierno,
    e.id_expediente,        -- row[1]
    m.nombre_municipio,     -- row[2]
    CONCAT(p.nombre, ' ', p.apellido_paterno, ' ', COALESCE(p.apellido_materno, '')), -- row[3]
    ta.nombre,              -- row[4]
    COALESCE(eq.descripcion_estatus, 'Asignada a Asesor') AS descripcion_estatus, -- row[5] ← fix aquí
    ede.nombre,             -- row[6]
    da.seguimiento,         -- row[7]
    da.fecha_notificacion,  -- row[8]
    COALESCE(e.bloqueado, false) AS bloqueado,  -- row[9]
    true AS tiene_ficha,    -- row[10]
    EXISTS (SELECT 1 FROM sustantiva.quejas_cir c WHERE c.id_queja = qj.id_queja) AS tiene_cir,       -- row[11]
    EXISTS (SELECT 1 FROM sustantiva.quejas_ari a WHERE a.id_queja = qj.id_queja) AS tiene_ari,       -- row[12]
    EXISTS (
    SELECT 1 FROM sustantiva.oficio_notificacion on2
    WHERE on2.folio_expediente = e.folio_gobierno
) AS tiene_oficio,
    EXISTS (
        SELECT 1 FROM sustantiva.quejas_respuestas_autoridad r
        WHERE r.id_queja = qj.id_queja
    ) AS tiene_contestacion, -- row[14]
    EXISTS (SELECT 1 FROM sustantiva.quejas_acci ac WHERE ac.id_queja = qj.id_queja) AS tiene_acci,   -- row[15]
    EXISTS (
        SELECT 1 FROM sustantiva.resolucion_final rf
        WHERE rf.id_expediente = e.id_expediente
    ) AS tiene_resolucion,  -- row[16]
      (COALESCE(qj.requisito_identificacion, false) = true
    AND COALESCE(qj.requisito_actos_fiscales, false) = true
    AND COALESCE(qj.requisito_narrativa_clara, false) = true
    AND COALESCE(qj.requisito_competencia_cedecon, false) = true
) AS checklist_completo
FROM sustantiva.expedientes e
LEFT JOIN sustantiva.contribuyentes c ON c.id_contribuyentes = e.id_contribuyente
LEFT JOIN catalogos.personas p ON p.id_persona = c.id_persona
LEFT JOIN sustantiva.detalle_asesoria da ON da.id_expediente = e.id_expediente
LEFT JOIN catalogos.cat_municipios m ON m.id_municipio = e.id_municipio
LEFT JOIN catalogos.tipo_acto_emitido ta ON ta.id_tipo_acto_emitido = da.id_tipo_acto_emitido
LEFT JOIN sustantiva.quejas qj ON qj.id_expediente = e.id_expediente
LEFT JOIN catalogos.cat_estatus_queja eq ON eq.id_estatus_queja = qj.id_estatus_queja
LEFT JOIN catalogos.estatus_detalle_expediente ede ON ede.id_estatus_detalle_expediente = da.id_estatus_detalle_expediente
WHERE (:search IS NULL OR :search = ''
       OR LOWER(CONCAT(p.nombre,' ',p.apellido_paterno)) LIKE LOWER(CONCAT('%', :search, '%'))
       OR LOWER(e.folio_gobierno) LIKE LOWER(CONCAT('%', :search, '%')))
AND (
    :estatus IS NULL
    OR :estatus = ''
    OR eq.descripcion_estatus = :estatus
    OR (:estatus = 'Asignada a Asesor' AND qj.id_estatus_queja IS NULL AND COALESCE(e.bloqueado, false) = false)
    OR (:estatus = 'Cerrada / Concluida' AND COALESCE(e.bloqueado, false) = true)
)
AND (:tipoTramite IS NULL OR :tipoTramite = ''
     OR CAST(e.id_tipo_tramite AS VARCHAR) = :tipoTramite)
ORDER BY da.fecha_notificacion DESC NULLS LAST
   """, nativeQuery = true)
List<Object[]> obtenerBandejaRaw(
    @Param("search") String search,
    @Param("estatus") String estatus,
    @Param("tipoTramite") String tipoTramite
);
}