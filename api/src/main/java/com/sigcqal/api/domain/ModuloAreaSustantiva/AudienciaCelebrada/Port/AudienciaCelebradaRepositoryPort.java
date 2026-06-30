package com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaCelebrada.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaCelebrada.Model.AudienciaCelebrada;

public interface AudienciaCelebradaRepositoryPort {

    AudienciaCelebrada save(AudienciaCelebrada audienciaCelebrada);

    Optional<AudienciaCelebrada> findById(Integer idAudienciaCelebrada);

    List<AudienciaCelebrada> findAll();

    boolean existsById(Integer idAudienciaCelebrada);
}
