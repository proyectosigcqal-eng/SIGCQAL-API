package com.sigcqal.api.domain.ClasificacionJuridica.Port;

import java.util.List;

import com.sigcqal.api.domain.ClasificacionJuridica.Model.ClasificacionJuridica;

public interface ClasificacionJuridicaRepositoryPort {

    ClasificacionJuridica saveClasification(ClasificacionJuridica clasificacionJuridica);
    List<ClasificacionJuridica> findByFolio(Integer idExpediente);
}
