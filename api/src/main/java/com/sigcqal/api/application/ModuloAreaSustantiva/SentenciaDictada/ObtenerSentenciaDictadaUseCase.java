package com.sigcqal.api.application.ModuloAreaSustantiva.SentenciaDictada;

import java.util.List;

import com.sigcqal.api.web.ModuloAreaSustantiva.SentenciaDictada.Dto.SentenciaDictadaResponseDTO;

public interface ObtenerSentenciaDictadaUseCase {

    List<SentenciaDictadaResponseDTO> listarTodos();
}
