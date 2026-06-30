package com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaEjecutoria.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaEjecutoria.Model.SentenciaEjecutoria;

public interface SentenciaEjecutoriaRepositoryPort {

    SentenciaEjecutoria save(SentenciaEjecutoria sentenciaEjecutoria);

    Optional<SentenciaEjecutoria> findById(Integer idSentenciaEjecutoria);

    List<SentenciaEjecutoria> findAll();

    boolean existsById(Integer idSentenciaEjecutoria);
}
