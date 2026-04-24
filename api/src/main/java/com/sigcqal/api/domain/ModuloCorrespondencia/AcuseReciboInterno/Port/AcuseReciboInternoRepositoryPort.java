package com.sigcqal.api.domain.ModuloCorrespondencia.AcuseReciboInterno.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseReciboInterno.Model.AcuseReciboInterno;

public interface AcuseReciboInternoRepositoryPort {

    List<AcuseReciboInterno> findByUsuario(Long idUsuario);

    Optional<AcuseReciboInterno> findById(Long id);

    AcuseReciboInterno save(AcuseReciboInterno acuse);

    boolean existePorMemorandum(Long idMemorandum);

List<AcuseReciboInterno> findByArea(Long idArea);
}