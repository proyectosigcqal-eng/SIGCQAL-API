package com.sigcqal.api.domain.Catalogo.Persona.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.Catalogo.Persona.Model.Persona;

public interface PersonaRepositoryPort {
    Persona save(Persona persona);
    Optional<Persona> findById(Long id);
    Optional<Persona> findByRfc(String rfc);
    List<Persona> findAll();
    void deleteById(Long id);

    // NUEVO: búsqueda por nombre (cualquiera de las 3 columnas) o RFC
    List<Persona> buscarPorNombreORfc(String texto);

}