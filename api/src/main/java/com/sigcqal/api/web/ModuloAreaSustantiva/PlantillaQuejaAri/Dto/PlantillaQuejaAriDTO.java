package com.sigcqal.api.web.ModuloAreaSustantiva.PlantillaQuejaAri.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantillaQuejaAriDTO {
    private Long id;
    private String nombrePlantilla;
    private String urlPlantillaQuejaAri;
    private LocalDateTime fechaCreacion;
    private Boolean activo;
}