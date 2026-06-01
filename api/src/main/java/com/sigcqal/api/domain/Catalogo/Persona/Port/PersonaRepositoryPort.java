package com.sigcqal.api.domain.Catalogo.Persona.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.Catalogo.Persona.Model.Persona;

public interface PersonaRepositoryPort {
    Optional<Persona> findById(Long id);
    List<Persona> findAll();
    Persona save(Persona persona);
}
