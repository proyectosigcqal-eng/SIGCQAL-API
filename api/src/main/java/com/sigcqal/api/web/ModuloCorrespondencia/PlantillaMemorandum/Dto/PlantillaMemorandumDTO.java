package com.sigcqal.api.web.ModuloCorrespondencia.PlantillaMemorandum.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantillaMemorandumDTO {
    private Long id;
    private String nombrePlantilla;
    private String urlPlantilla;
    private Boolean activo;
}
