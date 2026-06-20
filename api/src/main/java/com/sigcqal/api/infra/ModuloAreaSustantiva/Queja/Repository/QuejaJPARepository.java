package com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Entity.QuejaEntity;

@Repository
public interface QuejaJPARepository extends JpaRepository<QuejaEntity, Integer> {

    @Query("SELECT q FROM QuejaEntity q " +
           "LEFT JOIN FETCH q.expediente e " +
           "LEFT JOIN FETCH e.representanteLegal rep " +
           "LEFT JOIN FETCH e.contribuyente c " +
           "LEFT JOIN FETCH c.persona p_cont " +
           "LEFT JOIN FETCH q.asesor a " +
           "LEFT JOIN FETCH a.persona p_ase " +
           "WHERE q.idQueja = :idQueja")
    Optional<QuejaEntity> findByIdConRelaciones(@Param("idQueja") Integer idQueja);

    @Query("SELECT q FROM QuejaEntity q " +
           "LEFT JOIN FETCH q.expediente e " +
           "LEFT JOIN FETCH e.representanteLegal rep " +
           "LEFT JOIN FETCH e.contribuyente c " +
           "LEFT JOIN FETCH c.persona p_cont " +
           "LEFT JOIN FETCH q.asesor a " +
           "LEFT JOIN FETCH a.persona p_ase")
    List<QuejaEntity> findAllConRelaciones();

    Optional<QuejaEntity> findByExpediente_Id(Integer expedienteId);

    @Query(value = """
        SELECT q.id_expediente FROM sustantiva.quejas q
        JOIN sustantiva.expedientes e ON e.id_expediente = q.id_expediente
        WHERE e.folio_gobierno = :folio
        LIMIT 1
        """, nativeQuery = true)
    Optional<Integer> findIdExpedienteByFolio(@Param("folio") String folio);

    @Modifying
    @Query(value = """
        UPDATE sustantiva.quejas
        SET id_detalle_asesoria = :idDetalleAsesoria,
            ultima_actualizacion = NOW()
        WHERE id_expediente = :idExpediente
        """, nativeQuery = true)
    void admitirQueja(
            @Param("idDetalleAsesoria") Integer idDetalleAsesoria,
            @Param("idExpediente")      Integer idExpediente);

    // ✅ ID directo — evita problemas de encoding con texto
    @Modifying
    @Query(value = """
        UPDATE sustantiva.quejas
        SET id_estatus_queja     = 2,
            ultima_actualizacion = NOW()
        WHERE id_expediente = :idExpediente
        """, nativeQuery = true)
    void requerirAclaracion(@Param("idExpediente") Integer idExpediente);

    @Query(value = """
        SELECT id_detalle_asesoria FROM sustantiva.detalle_asesoria
        WHERE id_expediente = :idExpediente
        LIMIT 1
        """, nativeQuery = true)
    Optional<Integer> findIdDetalleByExpediente(@Param("idExpediente") Integer idExpediente);

    @Modifying
    @Query(value = """
        UPDATE sustantiva.quejas
        SET requisito_identificacion      = :identificacion,
            requisito_actos_fiscales      = :actosFiscales,
            requisito_narrativa_clara     = :narrativa,
            requisito_competencia_cedecon = :competencia,
            ultima_actualizacion          = NOW()
        WHERE id_expediente = (
            SELECT id_expediente FROM sustantiva.expedientes
            WHERE folio_gobierno = :folio LIMIT 1
        )
        """, nativeQuery = true)
    void actualizarRequisitos(
        @Param("folio")          String  folio,
        @Param("identificacion") Boolean identificacion,
        @Param("actosFiscales")  Boolean actosFiscales,
        @Param("narrativa")      Boolean narrativa,
        @Param("competencia")    Boolean competencia);

    @Query(value = """
        SELECT q.requisito_identificacion,
               q.requisito_actos_fiscales,
               q.requisito_narrativa_clara,
               q.requisito_competencia_cedecon
        FROM sustantiva.quejas q
        JOIN sustantiva.expedientes e ON e.id_expediente = q.id_expediente
        WHERE e.folio_gobierno = :folio
        LIMIT 1
        """, nativeQuery = true)
    List<Object[]> findRequisitosByFolio(@Param("folio") String folio);

    @Modifying
    @Query(value = """
        INSERT INTO sustantiva.quejas (
            id_expediente,
            id_asesor,
            id_detalle_asesoria,
            id_estatus_queja,
            requisito_identificacion,
            requisito_actos_fiscales,
            requisito_narrativa_clara,
            requisito_competencia_cedecon,
            ultima_actualizacion
        )
        SELECT
            e.id_expediente,
            e.id_asesor,
            da.id_detalle_asesoria,
            1,
            :identificacion,
            :actosFiscales,
            :narrativa,
            :competencia,
            NOW()
        FROM sustantiva.expedientes e
        LEFT JOIN sustantiva.detalle_asesoria da 
               ON da.id_expediente = e.id_expediente
        WHERE e.folio_gobierno = :folio
        LIMIT 1
        ON CONFLICT ON CONSTRAINT quejas_id_expediente_key DO UPDATE SET
            requisito_identificacion      = :identificacion,
            requisito_actos_fiscales      = :actosFiscales,
            requisito_narrativa_clara     = :narrativa,
            requisito_competencia_cedecon = :competencia,
            ultima_actualizacion          = NOW()
        """, nativeQuery = true)
    void upsertRequisitos(
        @Param("folio")          String  folio,
        @Param("identificacion") Boolean identificacion,
        @Param("actosFiscales")  Boolean actosFiscales,
        @Param("narrativa")      Boolean narrativa,
        @Param("competencia")    Boolean competencia
    );

    // ✅ Marca procede — estatus 1 = Asignada a Asesor con checklist completo
    @Modifying
    @Query(value = """
        UPDATE sustantiva.quejas
        SET id_estatus_queja     = 1,
            ultima_actualizacion = NOW()
        WHERE id_expediente = :idExpediente
        """, nativeQuery = true)
    void marcarProcede(@Param("idExpediente") Integer idExpediente);
}