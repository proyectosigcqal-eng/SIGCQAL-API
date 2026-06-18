package com.sigcqal.api.domain.ModuloAreaSustantiva.PlantillaQuejaAri.Port;

import java.util.List;
import com.sigcqal.api.domain.ModuloAreaSustantiva.PlantillaQuejaAri.Model.PlantillaQuejaAri;

public interface PlantillaQuejaAriRepositoryPort {
    List<PlantillaQuejaAri> findAll();
}