package com.sigcqal.api.domain.ModuloAreaSustantiva.Turnado.Port;

import com.sigcqal.api.domain.ModuloAreaSustantiva.Turnado.Model.AsesorDisponible;
import java.util.List;

public interface TurnadoPort {
    List<AsesorDisponible> findAsesoresDisponibles();
    void asignarAsesor(Integer idExpediente, Long idAsesor, Long idAsesorAnterior,
                       Integer idUsuario, String ip, String motivo);
    void actualizarCargaAsesor(Long idAsesor);
}