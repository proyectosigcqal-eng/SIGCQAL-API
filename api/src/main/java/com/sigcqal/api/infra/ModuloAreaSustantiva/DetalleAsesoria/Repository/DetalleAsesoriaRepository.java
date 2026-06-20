package com.sigcqal.api.infra.ModuloAreaSustantiva.DetalleAsesoria.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigcqal.api.domain.ModuloAreaSustantiva.DetalleAsesoria.Model.DetalleAsesoria;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;

public interface DetalleAsesoriaRepository
              extends JpaRepository<ExpedienteEntity, Integer> {

       @Query(value = """
                     SELECT
                     e.id_expediente,
                         e.folio_gobierno,
                         e.fecha_solicitud,
                         CONCAT(p.nombre,' ',p.apellido_paterno,' ',
                                COALESCE(p.apellido_materno,''))   AS nombre_completo,
                         ee.nombre                                  AS estatus_expediente,
                         m.nombre_municipio,
                         ta.nombre                                  AS nombre_acto,
                         a.nombre                                   AS nombre_autoridad,
                         ede.nombre                                 AS estatus_detalle,
                         da.calificacion_acto,
                         da.problematica,
                         da.seguimiento,
                         da.fecha_notificacion,
                         p.rfc,
                         p.curp,
                         p.telefono,
                         p.correo,
                         CONCAT(pas.nombre,' ',pas.apellido_paterno) AS nombre_asesor
                     FROM sustantiva.expedientes e
                     LEFT JOIN sustantiva.contribuyentes c
                            ON c.id_contribuyentes = e.id_contribuyente
                     LEFT JOIN catalogos.personas p
                            ON p.id_persona = c.id_persona
                     LEFT JOIN sustantiva.detalle_asesoria da
                            ON da.id_expediente = e.id_expediente
                     LEFT JOIN catalogos.cat_municipios m
                            ON m.id_municipio = e.id_municipio
                     LEFT JOIN catalogos.tipo_acto_emitido ta
                            ON ta.id_tipo_acto_emitido = da.id_tipo_acto_emitido
                     LEFT JOIN catalogos.autoridades a
                            ON a.id_autoridad = da.id_autoridad
                     LEFT JOIN catalogos.estatus_expediente ee
                            ON ee.id_estatus_expediente = e.id_estatus_expediente
                     LEFT JOIN catalogos.estatus_detalle_expediente ede
                            ON ede.id_estatus_detalle_expediente = da.id_estatus_detalle_expediente
                     LEFT JOIN sustantiva.asesores ase
                            ON ase.id_asesores = e.id_asesor
                     LEFT JOIN catalogos.personas pas
                            ON pas.id_persona = ase.id_persona
                     WHERE e.folio_gobierno = :folio
                     LIMIT 1
                     """, nativeQuery = true)
       Optional<DetalleAsesoria> findDetalleByFolio(@Param("folio") String folio);
}
