package com.sigcqal.api.application.ModuloAreaSustantiva.AudienciaEspera;

import java.util.List;

import com.sigcqal.api.web.ModuloAreaSustantiva.AudienciaEspera.Dto.AudienciaEsperaResponseDTO;

public interface ObtenerAudienciaEsperaUseCase {

    List<AudienciaEsperaResponseDTO> listarTodos();
}
