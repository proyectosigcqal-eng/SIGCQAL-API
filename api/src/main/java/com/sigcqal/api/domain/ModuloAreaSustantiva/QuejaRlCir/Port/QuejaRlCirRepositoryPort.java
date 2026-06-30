package com.sigcqal.api.domain.ModuloAreaSustantiva.QuejaRlCir.Port;

import java.util.List;
import java.util.Optional;
import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejaRlCir.Model.QuejaRlCir;

public interface QuejaRlCirRepositoryPort {
    QuejaRlCir save(QuejaRlCir quejaRlCir);
    List<QuejaRlCir> findAll();
    List<QuejaRlCir> findByIdResolucionFinal(Long idResolucionFinal);
    Optional<QuejaRlCir> buscarPorId(Long id);
}