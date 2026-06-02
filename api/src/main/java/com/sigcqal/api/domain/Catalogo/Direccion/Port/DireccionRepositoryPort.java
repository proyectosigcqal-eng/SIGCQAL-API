package com.sigcqal.api.domain.Catalogo.Direccion.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.Catalogo.Direccion.Model.Direccion;

public interface DireccionRepositoryPort {
    Direccion save(Direccion direccion);
    Optional<Direccion> findById(Long id);
    List<Direccion> findAll();
 
}