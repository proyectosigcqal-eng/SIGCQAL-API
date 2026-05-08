package com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoCorrespondencia.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoCorrespondencia.Model.SeguimientoCorrespondencia;

public interface ISeguimientoCorrespondenciaPort {
    SeguimientoCorrespondencia guardar(SeguimientoCorrespondencia seguimiento);
    List<SeguimientoCorrespondencia> listarTodos();
    List<SeguimientoCorrespondencia> listarPorCorrespondenciaId(Integer idCorrespondencia);
    Optional<SeguimientoCorrespondencia> buscarPorId(Long idSeguimiento); // ← agregar
    SeguimientoCorrespondencia actualizar(SeguimientoCorrespondencia seguimiento); // ← agregar
}