package com.sigcqal.api.domain.Catalogo.TipoActoEmitido.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.Catalogo.TipoActoEmitido.Model.TipoActoEmitido;

public interface TipoActoEmitidoRepositoryPort {
    Optional<TipoActoEmitido> findById(Long id);

    List<TipoActoEmitido> findAll();
}
