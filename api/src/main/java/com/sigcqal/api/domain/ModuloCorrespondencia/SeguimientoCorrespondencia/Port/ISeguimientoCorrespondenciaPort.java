package com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoCorrespondencia.Port;

import java.util.List;

import com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoCorrespondencia.Model.SeguimientoCorrespondencia;

public interface ISeguimientoCorrespondenciaPort {
    SeguimientoCorrespondencia guardar(SeguimientoCorrespondencia seguimientoCorrespondencia);

    List<SeguimientoCorrespondencia> listarTodos();

    List<SeguimientoCorrespondencia> listarPorCorrespondenciaId(Integer idCorrespondencia);
}
