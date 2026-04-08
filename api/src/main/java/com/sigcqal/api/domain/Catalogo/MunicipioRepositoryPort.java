package com.sigcqal.api.domain.Catalogo;

import java.util.List;
import java.util.Optional;

public interface MunicipioRepositoryPort {

    Optional<Municipio> findByName(String nombreMunicipio);
    
    List<Municipio> ListAll();
}
