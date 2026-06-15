package com.sigcqal.api.infra.ModuloAreaSustantiva.Turnado.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;

public interface ExpedienteTurnadoRepository
        extends JpaRepository<ExpedienteEntity, Integer> {

    // Actualiza el asesor asignado en el expediente
    @Modifying
    @Query(value = """
        UPDATE sustantiva.expedientes
        SET id_asesor = :idAsesor
        WHERE id_expediente = :idExpediente
        """, nativeQuery = true)
    void actualizarAsesor(
            @Param("idExpediente") Integer idExpediente,
            @Param("idAsesor")     Long    idAsesor);

    // Obtiene el asesor actual del expediente
   @Query(value = """
    SELECT e.id_asesor,
           e.id_expediente,
           e.folio_gobierno,
           e.bloqueado
    FROM sustantiva.expedientes e
    WHERE e.folio_gobierno = :folio
    LIMIT 1
    """, nativeQuery = true)
    List<Object[]> findDatosByFolio(@Param("folio") String folio);
}