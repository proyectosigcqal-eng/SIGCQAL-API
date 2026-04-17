package com.sigcqal.api.domain.ModuloCorrespondencia.Memorandum.Port;

import java.util.List;

import com.sigcqal.api.domain.ModuloCorrespondencia.Memorandum.Model.Memorandum;

public interface MemorandumRepositoryPort {
    Memorandum save(Memorandum memorandum);
    boolean existeFolio(String folio);
    List<Memorandum> findAll();
}
