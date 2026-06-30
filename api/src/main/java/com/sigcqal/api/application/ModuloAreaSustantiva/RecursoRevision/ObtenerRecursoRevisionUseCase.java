package com.sigcqal.api.application.ModuloAreaSustantiva.RecursoRevision;

import java.util.List;

import com.sigcqal.api.web.ModuloAreaSustantiva.RecursoRevision.Dto.RecursoRevisionResponseDTO;

public interface ObtenerRecursoRevisionUseCase {

    List<RecursoRevisionResponseDTO> listarTodos();
}
