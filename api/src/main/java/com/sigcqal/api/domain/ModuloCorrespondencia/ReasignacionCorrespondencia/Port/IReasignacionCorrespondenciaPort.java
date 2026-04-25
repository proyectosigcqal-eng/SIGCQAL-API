package com.sigcqal.api.domain.ModuloCorrespondencia.ReasignacionCorrespondencia.Port;

import java.util.List;

import com.sigcqal.api.domain.ModuloCorrespondencia.ReasignacionCorrespondencia.Model.ReasignacionCorrespondencia;

public interface IReasignacionCorrespondenciaPort {
    List<ReasignacionCorrespondencia> findPendientes();
}

