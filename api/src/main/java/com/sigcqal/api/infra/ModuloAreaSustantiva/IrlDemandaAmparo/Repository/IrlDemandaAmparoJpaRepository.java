package com.sigcqal.api.infra.ModuloAreaSustantiva.IrlDemandaAmparo.Repository;

import com.sigcqal.api.infra.ModuloAreaSustantiva.IrlDemandaAmparo.Entity.IrlDemandaAmparoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IrlDemandaAmparoJpaRepository
        extends JpaRepository<IrlDemandaAmparoEntity, Integer> {

    Optional<IrlDemandaAmparoEntity> findByIdExpediente(Integer idExpediente);
    Optional<IrlDemandaAmparoEntity> findByIdRepresentacionLegal(Integer idRepresentacionLegal);
    boolean existsByIdExpediente(Integer idExpediente);

     // ── Query que trae nombre, calle y colonia del quejoso via contribuyente ──
@Query(value = """
    SELECT
        CONCAT(p.nombre, ' ', p.apellido_paterno, ' ', COALESCE(p.apellido_materno, '')) AS nombre_quejoso,
        d.calle     AS calle_quejoso,
        d.num_ext   AS num_calle_quejoso,
        d.colonia   AS colonia_quejoso,
        d.cp        AS cp_quejoso
    FROM sustantiva.expedientes e
    JOIN sustantiva.contribuyentes c   ON c.id_contribuyentes = e.id_contribuyente
    JOIN catalogos.personas p          ON p.id_persona        = c.id_persona
    LEFT JOIN catalogos.direcciones d  ON d.id_direccion      = p.id_direccion
    WHERE e.id_expediente = :idExpediente
    LIMIT 1
    """, nativeQuery = true)
List<Object[]> findDatosQuejosoRaw(@Param("idExpediente") Integer idExpediente);

        }