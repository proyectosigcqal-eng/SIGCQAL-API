package com.sigcqal.api.domain.ModuloCorrespondencia.PlantillaMemorandum.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlantillaMemorandum {
    
private Long id;
private String nombrePlantilla;
private String urlPlantilla;
private Boolean activo;
}
