package com.sigcqal.api.domain.Catalogo.Autoridad.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.Catalogo.Autoridad.Model.Autoridad;

public interface AutoridadRepositoryPort {
    Optional<Autoridad> findById(Long id);
    List<Autoridad> findAll();
}
