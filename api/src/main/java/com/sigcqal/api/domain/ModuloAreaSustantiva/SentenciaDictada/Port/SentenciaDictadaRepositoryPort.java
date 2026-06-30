package com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaDictada.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaDictada.Model.SentenciaDictada;

public interface SentenciaDictadaRepositoryPort {

    SentenciaDictada save(SentenciaDictada sentenciaDictada);

    Optional<SentenciaDictada> findById(Integer idSentencia);

    List<SentenciaDictada> findAll();

    boolean existsById(Integer idSentencia);
}
