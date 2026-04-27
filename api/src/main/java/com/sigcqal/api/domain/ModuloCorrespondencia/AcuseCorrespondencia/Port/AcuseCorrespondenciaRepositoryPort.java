package com.sigcqal.api.domain.ModuloCorrespondencia.AcuseCorrespondencia.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseCorrespondencia.Model.AcuseCorrespondencia;

public interface AcuseCorrespondenciaRepositoryPort {

    AcuseCorrespondencia save(AcuseCorrespondencia acuse);

    Optional<AcuseCorrespondencia> findById(Long id);

    List<AcuseCorrespondencia> findByArea(Long idArea);
}