package com.sigcqal.api.domain.MAIQR_CU_28;

import java.util.List;
import java.util.Optional;


public interface ConvenioRepositoryPort {
    
    ConvenioMunicipio save(ConvenioMunicipio convenio); 
    
    Optional<ConvenioMunicipio> findByName(String nombreMunicipio);
    
    List<ConvenioMunicipio> ListAll();
}