package com.sigcqal.api.domain.ModuloAreaSustantiva.BandejaAsesoria.Port;

import com.sigcqal.api.domain.ModuloAreaSustantiva.BandejaAsesoria.Model.TramiteBandeja;
import java.util.List;

public interface BandejaAsesoriaPort {
    List<TramiteBandeja> obtenerBandeja(String search, String estatus, String tipoTramite);
}
