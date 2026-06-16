package com.sigcqal.api.infra.ModuloAreaSustantiva.PlantillaQuejaAri.Adapter;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.sigcqal.api.domain.ModuloAreaSustantiva.PlantillaQuejaAri.Model.PlantillaQuejaAri;
import com.sigcqal.api.domain.ModuloAreaSustantiva.PlantillaQuejaAri.Port.PlantillaQuejaAriRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.PlantillaQuejaAri.Entity.PlantillaQuejaAriEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.PlantillaQuejaAri.Repository.PlantillaQuejaAriRepositoryJpa;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PlantillaQuejaAriAdapter implements PlantillaQuejaAriRepositoryPort {

    private final PlantillaQuejaAriRepositoryJpa jpaRepository;

    @Override
    public List<PlantillaQuejaAri> findAll() {
        return jpaRepository.findAll().stream().map(this::mapToDomain).toList();
    }

    private PlantillaQuejaAri mapToDomain(PlantillaQuejaAriEntity entity) {
        return new PlantillaQuejaAri(
            entity.getId(),
            entity.getNombrePlantilla(),
            entity.getUrlPlantillaQuejaAri(),
            entity.getFechaCreacion(),
            entity.getActivo()
        );
    }
}