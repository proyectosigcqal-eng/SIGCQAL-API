package com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoMemorandum.Port;

import java.util.List;

import com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoMemorandum.Model.SeguimientoMemorandum;

public interface ISeguimientoMemorandumPort {
    SeguimientoMemorandum guardar(SeguimientoMemorandum seguimientoMemorandum);

    List<SeguimientoMemorandum> listarTodos();

    List<SeguimientoMemorandum> listarPorMemorandumId(Long idMemo);
}

