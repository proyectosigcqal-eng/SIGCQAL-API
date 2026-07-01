package com.sigcqal.api.application.ModuloAreaSustantiva.AudienciaCelebrada;

import com.sigcqal.api.web.ModuloAreaSustantiva.AudienciaCelebrada.Dto.AudienciaCelebradaRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.AudienciaCelebrada.Dto.AudienciaCelebradaResponseDTO;

public interface GuardarAudienciaCelebradaUseCase {

    AudienciaCelebradaResponseDTO ejecutar(AudienciaCelebradaRequestDTO request);
}
