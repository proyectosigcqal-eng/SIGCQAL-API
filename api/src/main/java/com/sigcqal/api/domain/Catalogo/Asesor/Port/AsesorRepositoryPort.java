package com.sigcqal.api.domain.Catalogo.Asesor.Port;

import java.util.List;

import com.sigcqal.api.domain.Catalogo.Asesor.Model.Asesor;

public interface AsesorRepositoryPort {
    List<Asesor> findAll();
}
