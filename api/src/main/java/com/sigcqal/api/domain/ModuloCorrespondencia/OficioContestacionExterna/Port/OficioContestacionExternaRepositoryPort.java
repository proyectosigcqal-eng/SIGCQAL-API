package com.sigcqal.api.domain.ModuloCorrespondencia.OficioContestacionExterna.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.ModuloCorrespondencia.OficioContestacionExterna.Model.OficioContestacionExterna;

public interface OficioContestacionExternaRepositoryPort {
    OficioContestacionExterna guardar(OficioContestacionExterna oficio);

    Optional<OficioContestacionExterna> buscarPorCorrespondencia(Long idCorrespondencia);

    List<OficioContestacionExterna> listarTodos();
}
