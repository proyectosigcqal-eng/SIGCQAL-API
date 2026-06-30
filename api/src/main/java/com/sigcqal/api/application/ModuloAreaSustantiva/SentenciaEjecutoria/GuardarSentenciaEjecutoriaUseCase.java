package com.sigcqal.api.application.ModuloAreaSustantiva.SentenciaEjecutoria;

import com.sigcqal.api.web.ModuloAreaSustantiva.SentenciaEjecutoria.Dto.SentenciaEjecutoriaRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.SentenciaEjecutoria.Dto.SentenciaEjecutoriaResponseDTO;

public interface GuardarSentenciaEjecutoriaUseCase {

    SentenciaEjecutoriaResponseDTO ejecutar(SentenciaEjecutoriaRequestDTO request);
}
