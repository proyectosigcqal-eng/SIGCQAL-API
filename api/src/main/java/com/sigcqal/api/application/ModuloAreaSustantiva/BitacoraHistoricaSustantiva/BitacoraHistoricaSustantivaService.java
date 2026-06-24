package com.sigcqal.api.application.ModuloAreaSustantiva.BitacoraHistoricaSustantiva;

import java.util.List;
import java.util.Collections; // <--- ERROR: Debes importar esto
import org.springframework.stereotype.Service;
import com.sigcqal.api.domain.ModuloAreaSustantiva.BitacoraHistoricaSustantiva.Model.BitacoraHistoricaSustantiva;
import com.sigcqal.api.domain.ModuloAreaSustantiva.BitacoraHistoricaSustantiva.Port.BitacoraHistoricaSustantivaRepositoryPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Queja.Port.QuejaRepositoryPort; // <--- IMPORTA TU REPOSITORIO DE QUEJAS AQUÍ
import lombok.RequiredArgsConstructor;

@Service("bitacoraSustantivaService")
@RequiredArgsConstructor
public class BitacoraHistoricaSustantivaService {

    private final BitacoraHistoricaSustantivaRepositoryPort repositoryPort;
    private final QuejaRepositoryPort quejaRepository; // <--- Asegúrate de tener este puerto

    // 1. Asegúrate de que este método realmente exista en tu clase
    public List<BitacoraHistoricaSustantiva> obtenerHistorial(Integer idQueja) {
    // Aquí usamos el nombre correcto que está definido en tu Port
    return repositoryPort.obtenerHistorialIntegral(idQueja); 
}

// Verifica que este método exista y esté bien escrito en tu Service
public List<BitacoraHistoricaSustantiva> obtenerHistorialPorFolio(String folio) {
    Integer idQueja = quejaRepository.findIdByFolio(folio);
    
    if (idQueja == null) {
        return Collections.emptyList(); // Asegúrate de tener importado java.util.Collections
    }
    
    return this.obtenerHistorial(idQueja); // Asegúrate de que el nombre del método en el Port sea exactamente este
}
}