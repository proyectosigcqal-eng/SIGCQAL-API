package com.sigcqal.api.application.ModuloAreaSustantiva.BandejaAsesoria;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sigcqal.api.domain.ModuloAreaSustantiva.BandejaAsesoria.Port.BandejaAsesoriaPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.BandejaAsesoria.Model.TramiteBandeja;
import com.sigcqal.api.infra.Catalogo.Asesor.Repository.AsesorJpaRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BandejaAsesoriaService {

    private final BandejaAsesoriaPort bandejaAsesoriaPort;
    private final AsesorJpaRepository asesorRepository;

    // ID de rol Asesor según tu tabla cat_roles
    private static final String ROL_ASESOR = "ROLE_Asesor";

    public List<TramiteBandeja> obtenerBandeja(String search, String estatus,
                                                String tipoTramite, String username,
                                                String rol) {
        String idTipoTramiteConvertido = traducirTipoTramite(tipoTramite);

        // Solo los asesores tienen restricción — admin y demás ven todo
        Long idAsesor = null;
       // En BandejaAsesoriaService — si es asesor pero no tiene registro, devolver lista vacía
        if (ROL_ASESOR.equals(rol)) {
            Optional<Long> idAsesorOpt = asesorRepository.findIdAsesorByUsername(username);
            if (idAsesorOpt.isEmpty()) {
                // Usuario marcado como Asesor pero sin registro en tabla asesores
                return List.of();
            }
            idAsesor = idAsesorOpt.get();
        }

        return bandejaAsesoriaPort.obtenerBandeja(
            search, estatus, idTipoTramiteConvertido, idAsesor
        );
    }

    private String traducirTipoTramite(String tipoTramiteFrontend) {
        if (tipoTramiteFrontend == null || tipoTramiteFrontend.trim().isEmpty()) return null;
        return switch (tipoTramiteFrontend) {
            case "ASESORIA_SIMPLIFICADA"   -> "1";
            case "QUEJAS_Y_RECLAMACIONES"  -> "2";
            case "REPRESENTACION_LEGAL"    -> "3";
            default -> null;
        };
    }
}