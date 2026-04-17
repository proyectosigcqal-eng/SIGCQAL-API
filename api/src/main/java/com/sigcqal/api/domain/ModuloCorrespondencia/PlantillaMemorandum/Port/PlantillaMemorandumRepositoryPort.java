package com.sigcqal.api.domain.ModuloCorrespondencia.PlantillaMemorandum.Port;

import java.util.List;

import com.sigcqal.api.domain.ModuloCorrespondencia.PlantillaMemorandum.Model.PlantillaMemorandum;

public interface PlantillaMemorandumRepositoryPort {

    List<PlantillaMemorandum> findAll();
    
}
