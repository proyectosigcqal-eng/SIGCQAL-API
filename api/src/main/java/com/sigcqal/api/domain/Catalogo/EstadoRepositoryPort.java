package com.sigcqal.api.domain.Catalogo;

import java.util.List;
import java.util.Optional;

public interface EstadoRepositoryPort {

    Optional<Estado> findByName(String nombreEstado);
    
    List<Estado> ListAll();
}