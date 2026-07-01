package com.sigcqal.api.application.ModuloAreaSustantiva.AudienciaEspera;

import com.sigcqal.api.web.ModuloAreaSustantiva.AudienciaEspera.Dto.AudienciaEsperaRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.AudienciaEspera.Dto.AudienciaEsperaResponseDTO;

public interface GuardarAudienciaEsperaUseCase {

    AudienciaEsperaResponseDTO ejecutar(AudienciaEsperaRequestDTO request);
}
