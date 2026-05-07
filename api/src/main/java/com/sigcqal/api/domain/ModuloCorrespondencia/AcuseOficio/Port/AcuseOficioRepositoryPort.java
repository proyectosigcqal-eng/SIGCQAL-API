package com.sigcqal.api.domain.ModuloCorrespondencia.AcuseOficio.Port;

import java.util.List;
import java.util.Optional;
import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseOficio.Model.AcuseOficio;

public interface AcuseOficioRepositoryPort {

    Optional<AcuseOficio> findById(Long id);

    AcuseOficio save(AcuseOficio acuse);

    List<AcuseOficio> findByAreaAndEsDelAreaTrue(Long idArea);

    boolean existePorOficio(Long idOficio);
}