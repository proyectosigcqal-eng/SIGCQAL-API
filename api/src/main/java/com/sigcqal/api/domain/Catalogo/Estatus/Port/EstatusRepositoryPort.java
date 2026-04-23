package com.sigcqal.api.domain.Catalogo.Estatus.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.Catalogo.Estatus.Model.Estatus;

public interface EstatusRepositoryPort {
    
    Optional<Estatus> findByName(String nombreEstatus);

    List<Estatus> listAll();
}