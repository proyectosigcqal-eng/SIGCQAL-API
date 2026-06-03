package com.sigcqal.api.application.ModuloAreaSustantiva.BandejaAsesoria;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sigcqal.api.domain.ModuloAreaSustantiva.BandejaAsesoria.Port.BandejaAsesoriaPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.BandejaAsesoria.Model.TramiteBandeja;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BandejaAsesoriaService {

    private final BandejaAsesoriaPort bandejaAsesoriaPort;

    public List<TramiteBandeja> obtenerBandeja(String search, String estatus, String tipoTramite) {
        
        // 1. Traducimos el texto del frontend al ID numérico de tu base de datos
        String idTipoTramiteConvertido = traducirTipoTramite(tipoTramite);

        System.out.println("DEBUG: Buscando con -> Search: " + search + ", Estatus: " + estatus + ", ID Tipo Tramite: " + idTipoTramiteConvertido);

        // 2. Llamamos al puerto usando el ID ya convertido
        return bandejaAsesoriaPort.obtenerBandeja(search, estatus, idTipoTramiteConvertido);
    }

    // ── Método Auxiliar de Traducción ──────────────────────────────────────
    private String traducirTipoTramite(String tipoTramiteFrontend) {
        
        // Si el frontend manda nulo o vacío (por ejemplo al cargar la página por primera vez)
        if (tipoTramiteFrontend == null || tipoTramiteFrontend.trim().isEmpty()) {
            return null; 
        }

        // Mapeamos los textos que manda React a los IDs numéricos de Postgres
        return switch (tipoTramiteFrontend) {
            case "ASESORIA_SIMPLIFICADA" -> "1";  // ⚠️ Cambia el "1" por tu ID real
            case "QUEJAS_Y_RECLAMACIONES" -> "2"; // ⚠️ Cambia el "2" por tu ID real
            case "REPRESENTACION_LEGAL" -> "3";   // ⚠️ Cambia el "3" por tu ID real
            default -> null; // Si mandan un texto desconocido, pasamos null
        };
    }
}