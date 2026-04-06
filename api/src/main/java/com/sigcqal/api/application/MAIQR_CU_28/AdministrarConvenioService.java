package com.sigcqal.api.application.MAIQR_CU_28;

import java.util.List;

import com.sigcqal.api.domain.MAIQR_CU_28.ConvenioMunicipio;
import com.sigcqal.api.domain.MAIQR_CU_28.ConvenioRepositoryPort;

public class AdministrarConvenioService {
    private final ConvenioRepositoryPort repositoryPort;

    public AdministrarConvenioService(ConvenioRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public ConvenioMunicipio registrarConvenio(ConvenioMunicipio nuevo) {
    
        repositoryPort.findByName(nuevo.getNombreMunicipio())
            .ifPresent(c -> {
                throw new RuntimeException("El municipio '" + nuevo.getNombreMunicipio() + "' ya tiene un convenio registrado.");
            });

        return repositoryPort.save(nuevo);
    }

    public List<ConvenioMunicipio> obtenerCatalogoVigente() {
        return repositoryPort.ListAll();
    }
}

