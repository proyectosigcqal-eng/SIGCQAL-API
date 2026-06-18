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
    m.nombre_municipio,
    CONCAT(p.nombre, ' ', p.apellido_paterno, ' ', COALESCE(p.apellido_materno, '')),
    ta.nombre,
    eq.descripcion_estatus,
    ede.nombre,
    da.seguimiento,
    da.fecha_notificacion,
    COALESCE(e.bloqueado, false) AS bloqueado,
    true AS tiene_ficha,
    EXISTS (SELECT 1 FROM sustantiva.quejas_cir c WHERE c.id_queja = qj.id_queja) AS tiene_cir,
    EXISTS (SELECT 1 FROM sustantiva.quejas_ari a WHERE a.id_queja = qj.id_queja) AS tiene_ari,
    EXISTS (
        SELECT 1 
        FROM sustantiva.quejas_oficios_autoridad o
        JOIN sustantiva.quejas_ari ar ON ar.id_ari = o.id_ari
        WHERE ar.id_queja = qj.id_queja
    ) AS tiene_oficio,
    EXISTS (
        SELECT 1 
        FROM sustantiva.quejas_respuestas_autoridad r
        WHERE r.id_queja = qj.id_queja
    ) AS tiene_contestacion,
    EXISTS (SELECT 1 FROM sustantiva.quejas_acci ac WHERE ac.id_queja = qj.id_queja) AS tiene_acci,
    EXISTS (
        SELECT 1 
        FROM sustantiva.resolucion_final rf 
        WHERE rf.id_expediente = e.id_expediente
    ) AS tiene_resolucion,
    COALESCE(
        qj.requisito_identificacion AND qj.requisito_actos_fiscales 
        AND qj.requisito_narrativa_clara AND qj.requisito_competencia_cedecon,
        false
    ) AS checklist_completo
FROM sustantiva.expedientes e
LEFT JOIN sustantiva.contribuyentes c ON c.id_contribuyentes = e.id_contribuyente
LEFT JOIN catalogos.personas p ON p.id_persona = c.id_persona
LEFT JOIN sustantiva.detalle_asesoria da ON da.id_expediente = e.id_expediente
LEFT JOIN catalogos.cat_municipios m ON m.id_municipio = e.id_municipio
LEFT JOIN catalogos.tipo_acto_emitido ta ON ta.id_tipo_acto_emitido = da.id_tipo_acto_emitido
LEFT JOIN sustantiva.quejas qj ON qj.id_expediente = e.id_expediente
LEFT JOIN catalogos.cat_estatus_queja eq ON eq.id_estatus_queja = qj.id_estatus_queja  -- ← corregido
LEFT JOIN catalogos.estatus_detalle_expediente ede ON ede.id_estatus_detalle_expediente = da.id_estatus_detalle_expediente
WHERE (:search IS NULL OR :search = ''
       OR LOWER(CONCAT(p.nombre,' ',p.apellido_paterno)) LIKE LOWER(CONCAT('%', :search, '%'))
       OR LOWER(e.folio_gobierno) LIKE LOWER(CONCAT('%', :search, '%')))
AND (:estatus IS NULL OR :estatus = '' OR eq.descripcion_estatus = :estatus)
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