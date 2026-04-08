package com.sigcqal.api.application.Catalogo;

import java.util.List;

import com.sigcqal.api.domain.Catalogo.Municipio;
import com.sigcqal.api.domain.Catalogo.MunicipioRepositoryPort;

public class MunicipioService {

        private final MunicipioRepositoryPort repositoryPort;

    public MunicipioService(MunicipioRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public List<Municipio> obtenerMunicipio() {
        return repositoryPort.ListAll();
    }
    
}
