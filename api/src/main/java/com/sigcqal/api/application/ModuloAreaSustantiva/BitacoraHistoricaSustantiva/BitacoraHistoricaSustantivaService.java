package com.sigcqal.api.application.ModuloAreaSustantiva.BitacoraHistoricaSustantiva;

import java.util.List;
import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.ModuloAreaSustantiva.BitacoraHistoricaSustantiva.Model.BitacoraHistoricaSustantiva;
import com.sigcqal.api.domain.ModuloAreaSustantiva.BitacoraHistoricaSustantiva.Port.BitacoraHistoricaSustantivaRepositoryPort;

import lombok.RequiredArgsConstructor; // Importante añadir esto

@Service("bitacoraSustantivaService")
@RequiredArgsConstructor // <--- Genera el constructor automáticamente
public class BitacoraHistoricaSustantivaService {

    // Cambiamos a 'private final'
    private final BitacoraHistoricaSustantivaRepositoryPort repositoryPort;

    public List<BitacoraHistoricaSustantiva> obtenerHistorial(Integer idQueja) {
        return repositoryPort.obtenerHistorialIntegral(idQueja);
    }
}