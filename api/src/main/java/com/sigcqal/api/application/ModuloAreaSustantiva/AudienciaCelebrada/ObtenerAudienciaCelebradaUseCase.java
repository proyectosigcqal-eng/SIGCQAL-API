package com.sigcqal.api.application.ModuloAreaSustantiva.AudienciaCelebrada;

import java.util.List;

import com.sigcqal.api.web.ModuloAreaSustantiva.AudienciaCelebrada.Dto.AudienciaCelebradaResponseDTO;

public interface ObtenerAudienciaCelebradaUseCase {

    List<AudienciaCelebradaResponseDTO> listarTodos();
}
