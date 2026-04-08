package com.sigcqal.api.application.Catalogo.Estado;

import java.util.List;

import com.sigcqal.api.domain.Catalogo.Estado.Model.Estado;
import com.sigcqal.api.domain.Catalogo.Estado.Port.EstadoRepositoryPort;

public class EstadoService {

        private final EstadoRepositoryPort repositoryPort;

    public EstadoService(EstadoRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public List<Estado> obtenerEstado() {
        return repositoryPort.ListAll();
    }
    
}

