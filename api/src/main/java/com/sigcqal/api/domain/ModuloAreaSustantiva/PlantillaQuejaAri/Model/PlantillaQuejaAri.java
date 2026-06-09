package com.sigcqal.api.domain.ModuloAreaSustantiva.PlantillaQuejaAri.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlantillaQuejaAri {
    private Long id;
    private String nombrePlantilla;
    private String urlPlantillaQuejaAri;
    private LocalDateTime fechaCreacion;
    private Boolean activo;
}