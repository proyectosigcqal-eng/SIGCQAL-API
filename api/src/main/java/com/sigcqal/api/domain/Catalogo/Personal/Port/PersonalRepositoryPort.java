package com.sigcqal.api.domain.Catalogo.Personal.Port;

import com.sigcqal.api.domain.Catalogo.Personal.Model.Personal;
import java.util.List;
import java.util.Optional;

public interface PersonalRepositoryPort {
    
    // Método para crear o actualizar
    Personal save(Personal personal);
    
    // Método para listar todo
    List<Personal> findAll();
    
    // Método para buscar por ID
    Optional<Personal> findById(Long id);
    
    // Método para eliminar
    void deleteById(Long id);
}
