package com.sigcqal.api.infra.ModuloAreaSustantiva.DemandaAmparo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigcqal.api.infra.ModuloAreaSustantiva.DemandaAmparo.Entity.DemandaAmparoEntity;

public interface DemandaAmparoJpaRepository extends JpaRepository<DemandaAmparoEntity, Integer> {

    @Query(value = """
            SELECT
                da.id_demanda_amparo,
                e.folio_gobierno,
                ar.num_expediente_oficial,
                per.nombre
            FROM sustantiva.irl_demanda_amparo da
            JOIN sustantiva.expedientes e ON e.id_expediente = da.id_expediente
            JOIN sustantiva.contribuyentes c ON c.id_contribuyentes = e.id_contribuyente
            JOIN catalogos.personas per ON per.id_persona = c.id_persona
            LEFT JOIN sustantiva.quejas q ON q.id_expediente = e.id_expediente
            LEFT JOIN sustantiva.quejas_ari ar ON ar.id_queja = q.id_queja
            WHERE da.id_demanda_amparo = :idDemandaAmparo
            ORDER BY ar.id_ari DESC NULLS LAST
            LIMIT 1
            """, nativeQuery = true)
    List<Object[]> findEncabezadoRawByAmparoId(@Param("idDemandaAmparo") Integer idDemandaAmparo);
}
