package com.sigcqal.api.domain.Catalogo.Rol.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.Catalogo.Rol.Model.Rol;

public interface RolRepositoryPort {
    Optional<Rol> findById(Long id);

    List<Rol> findAll();
}
