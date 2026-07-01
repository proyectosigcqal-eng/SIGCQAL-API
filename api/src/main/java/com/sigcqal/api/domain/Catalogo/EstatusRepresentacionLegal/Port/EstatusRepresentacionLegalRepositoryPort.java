package com.sigcqal.api.domain.Catalogo.EstatusRepresentacionLegal.Port;

import java.util.List;

import com.sigcqal.api.domain.Catalogo.EstatusRepresentacionLegal.Model.EstatusRepresentacionLegal;

public interface EstatusRepresentacionLegalRepositoryPort {

    List<EstatusRepresentacionLegal> findAll();
}
