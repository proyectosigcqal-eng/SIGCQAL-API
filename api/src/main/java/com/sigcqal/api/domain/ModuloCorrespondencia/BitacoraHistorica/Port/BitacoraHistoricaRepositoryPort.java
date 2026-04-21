package com.sigcqal.api.domain.ModuloCorrespondencia.BitacoraHistorica.Port;

import java.util.List;

import com.sigcqal.api.domain.ModuloCorrespondencia.BitacoraHistorica.Model.BitacoraHistorica;

public interface BitacoraHistoricaRepositoryPort {
    
    List<BitacoraHistorica> findByIdCorrespondencia(Long idCorrespondencia);
    BitacoraHistorica save(BitacoraHistorica bitacora);
}
