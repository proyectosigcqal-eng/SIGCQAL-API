package com.sigcqal.api.domain.ModuloAreaSustantiva.Queja.Port;

import java.util.List;
import java.util.Optional;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Queja.Model.Queja;

public interface QuejaRepositoryPort {
    Optional<Queja> findById(Integer idQueja);
    List<Queja> findAll();
}