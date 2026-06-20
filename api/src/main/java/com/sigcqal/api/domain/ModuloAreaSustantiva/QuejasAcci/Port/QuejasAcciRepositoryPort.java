package com.sigcqal.api.domain.ModuloAreaSustantiva.QuejasAcci.Port;


import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejasAcci.Model.QuejasAcci;
import java.util.List;
import java.util.Optional;

public interface QuejasAcciRepositoryPort {
    QuejasAcci guardar(QuejasAcci acci);
    Optional<QuejasAcci> findById(Long id);
    List<QuejasAcci> findByIdQueja(Long idQueja);
}
