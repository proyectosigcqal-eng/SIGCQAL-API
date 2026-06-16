package com.sigcqal.api.application.ModuloAreaSustantiva.PlantillaQuejaAri;

import java.util.List;
import org.springframework.stereotype.Service;
import com.sigcqal.api.domain.ModuloAreaSustantiva.PlantillaQuejaAri.Model.PlantillaQuejaAri;
import com.sigcqal.api.domain.ModuloAreaSustantiva.PlantillaQuejaAri.Port.PlantillaQuejaAriRepositoryPort;
import com.sigcqal.api.web.ModuloAreaSustantiva.PlantillaQuejaAri.Dto.PlantillaQuejaAriDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlantillaQuejaAriService {
    private final PlantillaQuejaAriRepositoryPort repositoryPort;

    public List<PlantillaQuejaAriDTO> listarPlantillas() {
        return repositoryPort.findAll().stream().map(this::mapToResponse).toList();
    }

    private PlantillaQuejaAriDTO mapToResponse(PlantillaQuejaAri dom) {
        PlantillaQuejaAriDTO dto = new PlantillaQuejaAriDTO();
        dto.setId(dom.getId());
        dto.setNombrePlantilla(dom.getNombrePlantilla());
        dto.setUrlPlantillaQuejaAri(dom.getUrlPlantillaQuejaAri());
        dto.setFechaCreacion(dom.getFechaCreacion());
        dto.setActivo(dom.getActivo());
        return dto;
    }
}