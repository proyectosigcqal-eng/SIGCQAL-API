package com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ContribuyenteEntity;

public interface ContribuyenteJPARepository extends JpaRepository<ContribuyenteEntity, Long> {
   // En tu ContribuyenteJPARepository:
Optional<ContribuyenteEntity> findByPersonaRfc(String rfc);
}
