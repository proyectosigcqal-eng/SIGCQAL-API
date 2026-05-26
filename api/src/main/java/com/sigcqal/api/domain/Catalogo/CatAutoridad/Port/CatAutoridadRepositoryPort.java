package com.sigcqal.api.domain.Catalogo.CatAutoridad.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.Catalogo.CatAutoridad.Model.CatAutoridad;

public interface CatAutoridadRepositoryPort {
    Optional<CatAutoridad> findById(Long id);
    List<CatAutoridad> findAll();
}
