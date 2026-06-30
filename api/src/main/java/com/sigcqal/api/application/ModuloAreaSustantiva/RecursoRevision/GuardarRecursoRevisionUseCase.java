package com.sigcqal.api.application.ModuloAreaSustantiva.RecursoRevision;

import com.sigcqal.api.web.ModuloAreaSustantiva.RecursoRevision.Dto.RecursoRevisionRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.RecursoRevision.Dto.RecursoRevisionResponseDTO;

public interface GuardarRecursoRevisionUseCase {

    RecursoRevisionResponseDTO ejecutar(RecursoRevisionRequestDTO request);
}
