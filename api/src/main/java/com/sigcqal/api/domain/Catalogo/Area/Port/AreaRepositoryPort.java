package com.sigcqal.api.domain.Catalogo.Area.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.Catalogo.Area.Model.Area;

public interface AreaRepositoryPort {
    Optional<Area> findById(Long id);

    List<Area> findAll();
}
