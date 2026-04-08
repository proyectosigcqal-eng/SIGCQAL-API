package com.sigcqal.api.domain.Catalogo.Municipio.Port;

import java.util.List;
import java.util.Optional;

import com.sigcqal.api.domain.Catalogo.Municipio.Model.Municipio;

public interface MunicipioRepositoryPort {

    Optional<Municipio> findByName(String nombreMunicipio);
    
    List<Municipio> ListAll();
}
