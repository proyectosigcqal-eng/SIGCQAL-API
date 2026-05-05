package com.sigcqal.api.domain.ModuloCorrespondencia.Correspondencia.Port;

import java.util.Optional;

import java.util.List;

import com.sigcqal.api.domain.ModuloCorrespondencia.Correspondencia.Model.Correspondencia;

public interface CorrespondenciaRepositoryPort {
    Correspondencia save(Correspondencia correspondencia);

    Optional<Correspondencia> findById(Long id);

    List<Correspondencia> findAll();

    boolean existsByNumeroOficio(String numeroOficio);

    Optional<Long> findLastConsecutivoByAnio(Integer anio);

    List<Correspondencia> findByIdArea(Long idArea);

    List<Correspondencia> findByIdAreaWithoutAcuse(Long idArea);
}
