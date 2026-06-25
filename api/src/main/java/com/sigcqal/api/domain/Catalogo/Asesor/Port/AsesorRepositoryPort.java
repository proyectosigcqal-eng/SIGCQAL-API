package com.sigcqal.api.domain.Catalogo.Asesor.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.Catalogo.Asesor.Model.Asesor;

public interface AsesorRepositoryPort {
    List<Asesor> findAll();
    Optional<Asesor> findById(Long id); 
    Asesor save(Asesor asesor);
    void actualizar(Long id, Asesor asesor);
    void darBaja(Long id);
}
