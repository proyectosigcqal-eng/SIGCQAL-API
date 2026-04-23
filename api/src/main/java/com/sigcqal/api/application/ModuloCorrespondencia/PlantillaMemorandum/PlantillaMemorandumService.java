package com.sigcqal.api.application.ModuloCorrespondencia.PlantillaMemorandum;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.ModuloCorrespondencia.PlantillaMemorandum.Model.PlantillaMemorandum;
import com.sigcqal.api.domain.ModuloCorrespondencia.PlantillaMemorandum.Port.PlantillaMemorandumRepositoryPort;
import com.sigcqal.api.web.ModuloCorrespondencia.PlantillaMemorandum.Dto.PlantillaMemorandumDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlantillaMemorandumService {
    private final PlantillaMemorandumRepositoryPort repositoryPort;

    public List<PlantillaMemorandumDTO> listarPlantillas() {
        return repositoryPort.findAll().stream().map(this::mapToResponse).toList();
    }

    private PlantillaMemorandumDTO mapToResponse(PlantillaMemorandum dom) {
        PlantillaMemorandumDTO dto = new PlantillaMemorandumDTO();
        dto.setId(dom.getId());
        dto.setNombrePlantilla(dom.getNombrePlantilla());
        dto.setUrlPlantilla(dom.getUrlPlantilla());
        dto.setActivo(dom.getActivo());
        return dto;
    }
}
