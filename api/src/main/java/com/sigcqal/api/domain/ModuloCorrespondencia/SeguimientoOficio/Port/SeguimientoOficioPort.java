package com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoOficio.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoOficio.Model.SeguimientoOficio;

public interface SeguimientoOficioPort {
    SeguimientoOficio guardar(SeguimientoOficio seguimientoOficio);
    List<SeguimientoOficio> listarTodos();
    List<SeguimientoOficio> listarPorOficioId(Integer idOficio);
    Optional<SeguimientoOficio> buscarPorId(Integer idSeguimiento);
}