package com.sigcqal.api.infra.ModuloCorrespondencia.PlantillaMemorandum.Adapter;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.sigcqal.api.domain.ModuloCorrespondencia.PlantillaMemorandum.Model.PlantillaMemorandum;
import com.sigcqal.api.domain.ModuloCorrespondencia.PlantillaMemorandum.Port.PlantillaMemorandumRepositoryPort;

@Repository
public class PlantillaMemorandumAdapter implements PlantillaMemorandumRepositoryPort {

    @Override
    public List<PlantillaMemorandum> findAll() {
        return Collections.emptyList();
    }

}
