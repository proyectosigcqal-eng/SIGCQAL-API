package com.sigcqal.api.domain.ModuloCorrespondencia.ReasignacionMemorandum.Port;

import java.util.List;

import com.sigcqal.api.domain.ModuloCorrespondencia.ReasignacionMemorandum.Model.ReasignacionMemorandum;

public interface IReasignacionMemorandumPort {
    List<ReasignacionMemorandum> findPendientes();
}

