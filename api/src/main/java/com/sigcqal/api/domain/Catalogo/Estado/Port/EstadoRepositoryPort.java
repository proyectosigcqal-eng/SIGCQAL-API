package com.sigcqal.api.domain.Catalogo.Estado.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.Catalogo.Estado.Model.Estado;

public interface EstadoRepositoryPort {

    Optional<Estado> findByName(String nombreEstado);
    
    List<Estado> ListAll();
}