package com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Entity.ResolucionFinalEntity;

public interface ResolucionFinalJPARepository extends JpaRepository<ResolucionFinalEntity, Integer> {
    Optional<ResolucionFinalEntity> findByIdExpediente(Integer idExpediente);

   @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
       "FROM ExpedienteEntity e " +
       "WHERE e.id = :idExpediente AND e.estatusExpediente.id = :idEstatus")
boolean expedienteEnDictaminacion(
    @Param("idExpediente") Integer idExpediente,
    @Param("idEstatus") Long idEstatus
);
}