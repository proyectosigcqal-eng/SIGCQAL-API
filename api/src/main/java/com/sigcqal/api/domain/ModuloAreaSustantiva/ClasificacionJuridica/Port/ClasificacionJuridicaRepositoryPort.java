package com.sigcqal.api.domain.ModuloAreaSustantiva.ClasificacionJuridica.Port;

import java.util.List;

import com.sigcqal.api.domain.ModuloAreaSustantiva.ClasificacionJuridica.Model.ClasificacionJuridica;

public interface ClasificacionJuridicaRepositoryPort {

    ClasificacionJuridica saveClasification(ClasificacionJuridica clasificacionJuridica);
    List<ClasificacionJuridica> findByFolio(Integer idExpediente);
    
}
