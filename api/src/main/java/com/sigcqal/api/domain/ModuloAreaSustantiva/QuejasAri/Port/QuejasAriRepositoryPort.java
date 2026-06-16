package com.sigcqal.api.domain.ModuloAreaSustantiva.QuejasAri.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejasAri.Model.QuejasAri;

public interface QuejasAriRepositoryPort {
    QuejasAri save(QuejasAri quejasAri);
    boolean existeNumExpediente(String numExpediente);
    List<QuejasAri> findAll();
    List<QuejasAri> findByIdQueja(Long idQueja);
    Optional<QuejasAri> buscarPorId(Long id);
}
