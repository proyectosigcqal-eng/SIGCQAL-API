package com.sigcqal.api.domain.ModuloAreaSustantiva.BandejaRepresentacionLegal.Port;
 
import com.sigcqal.api.domain.ModuloAreaSustantiva.BandejaRepresentacionLegal.Model.RepresentacionBandeja;
import java.util.List;
 
public interface BandejaRepresentacionPort {
    List<RepresentacionBandeja> obtenerBandeja(
        Boolean esEvolucion, 
        String search,
        String estatus,
        String tipoTramite
    );
}
 