package com.sigcqal.api.domain.Catalogo.CatEstatusSustantiva.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.Catalogo.CatEstatusSustantiva.Model.CatEstatusSustantiva;

public interface CatEstatusSustantivaRepositoryPort {
    Optional<CatEstatusSustantiva> findById(Long id);
    List<CatEstatusSustantiva> findAll();
}
