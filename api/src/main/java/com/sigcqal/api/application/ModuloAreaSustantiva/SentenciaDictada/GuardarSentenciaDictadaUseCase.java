package com.sigcqal.api.application.ModuloAreaSustantiva.SentenciaDictada;

import com.sigcqal.api.web.ModuloAreaSustantiva.SentenciaDictada.Dto.SentenciaDictadaRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.SentenciaDictada.Dto.SentenciaDictadaResponseDTO;

public interface GuardarSentenciaDictadaUseCase {

    SentenciaDictadaResponseDTO ejecutar(SentenciaDictadaRequestDTO request);
}
