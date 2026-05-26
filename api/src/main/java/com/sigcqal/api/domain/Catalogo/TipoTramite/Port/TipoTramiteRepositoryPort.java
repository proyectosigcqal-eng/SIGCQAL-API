package com.sigcqal.api.domain.Catalogo.TipoTramite.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.Catalogo.TipoTramite.Model.TipoTramite;

public interface TipoTramiteRepositoryPort {
    Optional<TipoTramite> findById(Long id);

    List<TipoTramite> findAll();
}
