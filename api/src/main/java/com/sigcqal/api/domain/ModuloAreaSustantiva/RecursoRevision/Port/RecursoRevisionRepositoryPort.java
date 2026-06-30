package com.sigcqal.api.domain.ModuloAreaSustantiva.RecursoRevision.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.ModuloAreaSustantiva.RecursoRevision.Model.RecursoRevision;

public interface RecursoRevisionRepositoryPort {

    RecursoRevision save(RecursoRevision recursoRevision);

    Optional<RecursoRevision> findById(Integer idRecursoRevision);

    List<RecursoRevision> findAll();

    boolean existsById(Integer idRecursoRevision);
}
