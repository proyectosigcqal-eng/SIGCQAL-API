package com.sigcqal.api.application.Catalogo;

import java.util.List;

import com.sigcqal.api.domain.Catalogo.Estado;
import com.sigcqal.api.domain.Catalogo.EstadoRepositoryPort;

public class EstadoService {

        private final EstadoRepositoryPort repositoryPort;

    public EstadoService(EstadoRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public List<Estado> obtenerEstado() {
        return repositoryPort.ListAll();
    }
    
}

