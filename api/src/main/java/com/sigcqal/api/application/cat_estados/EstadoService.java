package com.sigcqal.api.application.cat_estados;

import java.util.List;

import com.sigcqal.api.domain.cat_estados.Estado;
import com.sigcqal.api.domain.cat_estados.EstadoRepositoryPort;

public class EstadoService {

        private final EstadoRepositoryPort repositoryPort;

    public EstadoService(EstadoRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public List<Estado> obtenerEstado() {
        return repositoryPort.ListAll();
    }
    
}

