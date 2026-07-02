package com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Entity.ResolucionFinalEntity;

@Repository
public interface ResolucionFinalJPARepository extends JpaRepository<ResolucionFinalEntity, Integer> {

    List<ResolucionFinalEntity> findByIdExpediente(Integer idExpediente);

    boolean existsByIdExpediente(Integer idExpediente);

  @Query(value = """
    SELECT 
        e.id_expediente,
        ar.id_ari,
        r.id_respuesta_autoridad,
        qj.id_estatus_queja,
        e.id_estatus_expediente,
        e.fecha_solicitud,
        r.numero_oficio,
        r.fecha_oficio
    FROM sustantiva.expedientes e
    JOIN sustantiva.quejas qj ON qj.id_expediente = e.id_expediente
    LEFT JOIN sustantiva.quejas_ari ar ON ar.id_queja = qj.id_queja
    LEFT JOIN sustantiva.quejas_respuestas_autoridad r ON r.id_queja = qj.id_queja
    WHERE e.folio_gobierno = :folio
    ORDER BY ar.id_ari DESC NULLS LAST, r.id_respuesta_autoridad DESC NULLS LAST
    LIMIT 1
    """, nativeQuery = true)
List<Object[]> findDatosPreviosByFolio(@Param("folio") String folio);


@Query(value = """
    SELECT 
        e.id_expediente,
        ar.id_ari,
        r.id_respuesta_autoridad,
        qj.id_estatus_queja,
        e.id_estatus_expediente
    FROM sustantiva.expedientes e
    JOIN sustantiva.quejas qj ON qj.id_expediente = e.id_expediente
    LEFT JOIN sustantiva.quejas_ari ar ON ar.id_queja = qj.id_queja
    LEFT JOIN sustantiva.quejas_respuestas_autoridad r ON r.id_queja = qj.id_queja
    WHERE e.id_expediente = :idExpediente
    ORDER BY ar.id_ari DESC NULLS LAST, r.id_respuesta_autoridad DESC NULLS LAST
    LIMIT 1
    """, nativeQuery = true)
List<Object[]> findDatosPreviosByFolioExpediente(
    @Param("idExpediente") Integer idExpediente);


    
}