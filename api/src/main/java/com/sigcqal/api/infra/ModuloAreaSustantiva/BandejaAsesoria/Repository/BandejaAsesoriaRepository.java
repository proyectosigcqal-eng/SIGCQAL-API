package com.sigcqal.api.infra.ModuloAreaSustantiva.BandejaAsesoria.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import java.util.List;

// Usa ExpedienteEntity como entidad base — es la tabla principal del query
@Repository
public interface BandejaAsesoriaRepository extends JpaRepository<ExpedienteEntity, Integer> {

    @Query(value = """
        SELECT 
            e.folio_gobierno,
            m.nombre_municipio,
            CONCAT(p.nombre, ' ', p.apellido_paterno, ' ', COALESCE(p.apellido_materno, '')),
            ta.nombre,
            es.nombre,
            ede.nombre,
            da.seguimiento,
            TO_CHAR(da.fecha_notificacion, 'YYYY-MM-DD HH24:MI'),
            false,
            true
        FROM sustantiva.expedientes e
        LEFT JOIN sustantiva.contribuyentes c ON c.id_contribuyentes = e.id_contribuyente
        LEFT JOIN catalogos.personas p ON p.id_persona = c.id_persona
        LEFT JOIN sustantiva.detalle_asesoria da ON da.id_expediente = e.id_expediente
        LEFT JOIN catalogos.cat_municipios m ON m.id_municipio = e.id_municipio
        LEFT JOIN catalogos.tipo_acto_emitido ta ON ta.id_tipo_acto_emitido = da.id_tipo_acto_emitido
        LEFT JOIN catalogos.estatus_expediente es ON es.id_estatus_expediente = e.id_estatus_expediente
        LEFT JOIN catalogos.estatus_detalle_expediente ede ON ede.id_estatus_detalle_expediente = da.id_estatus_detalle_expediente
        WHERE (:search IS NULL OR :search = ''
               OR LOWER(CONCAT(p.nombre,' ',p.apellido_paterno)) LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(e.folio_gobierno) LIKE LOWER(CONCAT('%', :search, '%')))
        AND (:estatus IS NULL OR :estatus = '' OR es.nombre = :estatus)
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