package com.sigcqal.api.domain.ModuloCorrespondencia.Oficio.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.ModuloCorrespondencia.Oficio.Model.Oficio;

public interface OficioRepositoryPort {
    Oficio save(Oficio oficio);
    boolean existeFolio(String folio);
    List<Oficio> findAll();

    List<Oficio> findByArea(Long idArea);
    Optional<Oficio> buscarPorId(Long id);

List<Oficio> findSinAcuseByArea(Long idArea);
}
