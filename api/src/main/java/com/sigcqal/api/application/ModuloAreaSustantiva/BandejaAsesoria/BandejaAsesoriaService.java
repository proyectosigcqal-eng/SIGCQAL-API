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
        return bandejaAsesoriaPort.obtenerBandeja(search, estatus, tipoTramite);
    }
}
