package com.sigcqal.api.infra.ModuloAreaSustantiva.DiaInahabil.Repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ExpedientePrevencionJpaRepository
        extends JpaRepository<ExpedienteEntity, Integer> {

    // Busca la fecha en que el expediente entró a "En Prevención"
    // Ajusta el nombre/valor del estatus según tu catálogo
    @Query(value = """
        SELECT e.fecha_solicitud
        FROM sustantiva.expedientes e
        JOIN catalogos.estatus_expediente ee
          ON ee.id_estatus_expediente = e.id_estatus_expediente
        WHERE e.folio_gobierno = :folio
          AND UPPER(ee.nombre) LIKE '%PREVENCI%'
        LIMIT 1
        """, nativeQuery = true)
    Optional<LocalDateTime> findFechaPrevencionByFolio(@Param("folio") String folio);
}
