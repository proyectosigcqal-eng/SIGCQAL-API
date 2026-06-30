package com.sigcqal.api.application.ModuloAreaSustantiva.SentenciaEjecutoria;

import java.util.List;

import com.sigcqal.api.web.ModuloAreaSustantiva.SentenciaEjecutoria.Dto.SentenciaEjecutoriaResponseDTO;

public interface ObtenerSentenciaEjecutoriaUseCase {

    List<SentenciaEjecutoriaResponseDTO> listarTodos();
}
