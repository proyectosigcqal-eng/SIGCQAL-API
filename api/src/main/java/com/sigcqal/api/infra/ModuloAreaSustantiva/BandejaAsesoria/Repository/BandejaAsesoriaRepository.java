package com.sigcqal.api.infra.ModuloAreaSustantiva.BandejaAsesoria.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BandejaAsesoriaRepository extends JpaRepository<Object, Long> {

    @Query(value = """
        SELECT 
            e.folio_gobierno as folio,
            m.nombre_municipio as municipioProcedencia,
            CONCAT(p.nombre, ' ', p.apellido_paterno, ' ', p.apellido_materno) as contribuyente,
            ta.nombre as tipoActo,
            es.nombre as estatusPrincipal,
            ede.nombre as estatusSecundario,
            da.seguimiento as ultimaModificacionDescripcion,
            da.fecha_notificacion as ultimaModificacionTimestamp,
            COALESCE(false, false) as tieneBitacora,
            COALESCE(false, false) as tieneFicha
        FROM sustantiva.expedientes e
        LEFT JOIN sustantiva.contribuyentes c ON c.id_contribuyentes = e.id_contribuyentes
        LEFT JOIN catalogos.personas p ON p.id_persona = c.id_persona
        LEFT JOIN sustantiva.detalle_asesoria da ON da.id_expedientes = e.id_expedientes
        LEFT JOIN catalogos.cat_municipios m ON m.id_municipio = e.id_municipio
        LEFT JOIN catalogos.cat_tipo_acto_emitido ta ON ta.id_tipo_acto_emitido = da.id_tipo_acto_emitido
        LEFT JOIN catalogos.cat_estatus_expediente es ON es.id_estatus_expediente = e.id_estatus_expediente
        LEFT JOIN catalogos.cat_estatus_detalle_expediente ede ON ede.id_estatus_detalle_expediente = da.id_estatus_detalle_expediente
        WHERE (CAST(:search AS VARCHAR) IS NULL OR CAST(:search AS VARCHAR) = '' 
               OR LOWER(CONCAT(p.nombre,' ',p.apellido_paterno)) LIKE LOWER(CONCAT('%', CAST(:search AS VARCHAR), '%'))
               OR LOWER(e.folio_gobierno) LIKE LOWER(CONCAT('%', CAST(:search AS VARCHAR), '%')))
        AND (CAST(:estatus AS VARCHAR) IS NULL OR CAST(:estatus AS VARCHAR) = '' OR es.nombre = CAST(:estatus AS VARCHAR))
        AND (CAST(:tipoTramite AS VARCHAR) IS NULL OR CAST(:tipoTramite AS VARCHAR) = '' OR CAST(e.id_tipo_tramite AS VARCHAR) = CAST(:tipoTramite AS VARCHAR))
        ORDER BY da.fecha_notificacion DESC NULLS LAST
        """, nativeQuery = true)
    List<Object[]> obtenerBandejaRaw(
        @Param("search") String search,
        @Param("estatus") String estatus,
        @Param("tipoTramite") String tipoTramite
    );
}
