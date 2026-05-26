package com.sigcqal.api.domain.Catalogo.CatTipoProceso.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.Catalogo.CatTipoProceso.Model.CatTipoProceso;

public interface CatTipoProcesoRepositoryPort {
    Optional<CatTipoProceso> findById(Long id);
    List<CatTipoProceso> findAll();
}
