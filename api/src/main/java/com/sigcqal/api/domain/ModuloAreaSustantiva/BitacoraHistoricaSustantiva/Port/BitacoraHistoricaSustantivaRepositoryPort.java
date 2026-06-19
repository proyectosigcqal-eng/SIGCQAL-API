package com.sigcqal.api.domain.ModuloAreaSustantiva.BitacoraHistoricaSustantiva.Port;
import java.util.List;

import com.sigcqal.api.domain.ModuloAreaSustantiva.BitacoraHistoricaSustantiva.Model.BitacoraHistoricaSustantiva;

public interface BitacoraHistoricaSustantivaRepositoryPort {
    /**
     * Obtiene y consolida el historial completo de una queja,
     * consultando los 8 orígenes de datos definidos.
     * * @param idQueja ID único de la queja a consultar.
     * @return Lista de eventos ordenados cronológicamente.
     */
    List<BitacoraHistoricaSustantiva> obtenerHistorialIntegral(Integer idQueja);
}