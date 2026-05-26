package com.sigcqal.api.domain.Catalogo.TipoEntrada.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.Catalogo.TipoEntrada.Model.TipoEntrada;

public interface TipoEntradaRepositoryPort {
    Optional<TipoEntrada> findById(Long id);

    List<TipoEntrada> findAll();
}
