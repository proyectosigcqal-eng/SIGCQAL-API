package com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ContribuyenteEntity;

public interface ContribuyenteJPARepository extends JpaRepository<ContribuyenteEntity, Long> {
   Optional<ContribuyenteEntity> findByPersonaRfc(String rfc);

   @Query("SELECT c FROM ContribuyenteEntity c WHERE c.persona.id = :idPersona")
   Optional<ContribuyenteEntity> findByIdPersona(@Param("idPersona") Long idPersona);
}
