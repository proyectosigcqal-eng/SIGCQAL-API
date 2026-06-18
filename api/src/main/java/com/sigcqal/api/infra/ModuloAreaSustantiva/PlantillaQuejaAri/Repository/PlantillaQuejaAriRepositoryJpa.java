package com.sigcqal.api.infra.ModuloAreaSustantiva.PlantillaQuejaAri.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.PlantillaQuejaAri.Entity.PlantillaQuejaAriEntity;

@Repository
public interface PlantillaQuejaAriRepositoryJpa extends JpaRepository<PlantillaQuejaAriEntity, Long> {
}