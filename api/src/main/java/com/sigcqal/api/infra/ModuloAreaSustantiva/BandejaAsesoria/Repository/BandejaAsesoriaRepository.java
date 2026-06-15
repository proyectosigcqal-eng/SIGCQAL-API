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
        eq.descripcion_estatus, -- ¡Cambio 1: Seleccionamos de la nueva tabla!
        ede.nombre,
        da.seguimiento,
        da.fecha_notificacion,
        false,
        true
        FROM sustantiva.expedientes e
        LEFT JOIN sustantiva.contribuyentes c ON c.id_contribuyentes = e.id_contribuyente
        LEFT JOIN catalogos.personas p ON p.id_persona = c.id_persona
        LEFT JOIN sustantiva.detalle_asesoria da ON da.id_expediente = e.id_expediente
        LEFT JOIN catalogos.cat_municipios m ON m.id_municipio = e.id_municipio
        LEFT JOIN catalogos.tipo_acto_emitido ta ON ta.id_tipo_acto_emitido = da.id_tipo_acto_emitido
        
        -- ¡Cambio 2: El JOIN ahora apunta a cat_estatus_queja!
        -- (Asegúrate de que 'id_estatus_queja' sea el nombre real de tu llave foránea en 'expedientes')
        LEFT JOIN catalogos.cat_estatus_queja eq ON eq.id_estatus_queja = e.id_estatus_expediente
        
        LEFT JOIN catalogos.estatus_detalle_expediente ede ON ede.id_estatus_detalle_expediente = da.id_estatus_detalle_expediente
        WHERE (:search IS NULL OR :search = ''
               OR LOWER(CONCAT(p.nombre,' ',p.apellido_paterno)) LIKE LOWER(CONCAT('%', :search, '%'))
               OR LOWER(e.folio_gobierno) LIKE LOWER(CONCAT('%', :search, '%')))
               
        -- ¡Cambio 3: Filtramos usando descripcion_estatus!
        AND (:estatus IS NULL OR :estatus = '' OR eq.descripcion_estatus = :estatus) 
        AND (:tipoTramite IS NULL OR :tipoTramite = '' 
             OR CAST(e.id_tipo_tramite AS VARCHAR) = :tipoTramite)
        ORDER BY NULLIF(da.fecha_notificacion, '')::timestamp DESC NULLS LAST
        """, nativeQuery = true)
    List<Object[]> obtenerBandejaRaw(
        @Param("search") String search,
        @Param("estatus") String estatus,
        @Param("tipoTramite") String tipoTramite
    );
}