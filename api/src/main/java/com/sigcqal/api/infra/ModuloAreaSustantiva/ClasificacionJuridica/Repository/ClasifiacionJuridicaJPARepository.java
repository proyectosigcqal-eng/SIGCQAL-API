package com.sigcqal.api.infra.ModuloAreaSustantiva.ClasificacionJuridica.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigcqal.api.infra.ModuloAreaSustantiva.ClasificacionJuridica.Entity.ClasificacionJuridicaEntity;

public interface ClasifiacionJuridicaJPARepository extends JpaRepository<ClasificacionJuridicaEntity, Integer> {

     @Query(
    value = "SELECT * FROM sustantiva.detalle_asesoria WHERE id_expediente = :idExpediente",
    nativeQuery = true
)
List<ClasificacionJuridicaEntity> findAllByIdExpediente(@Param("idExpediente") Integer idExpediente);

}
