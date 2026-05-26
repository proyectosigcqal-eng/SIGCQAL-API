package com.sigcqal.api.domain.Catalogo.ControlFoliosConfig.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.Catalogo.ControlFoliosConfig.Model.ControlFoliosConfig;

public interface ControlFoliosConfigRepositoryPort {
    Optional<ControlFoliosConfig> findById(Long id);

    List<ControlFoliosConfig> findAll();
}
