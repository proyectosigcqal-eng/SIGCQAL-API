package com.sigcqal.api.domain.ModuloAreaSustantiva.OficioNotificacion.Port;


import com.sigcqal.api.domain.ModuloAreaSustantiva.OficioNotificacion.Model.OficioNotificacion;
import java.util.List;

public interface OficioNotificacionRepositoryPort {
    OficioNotificacion guardar(OficioNotificacion oficio);
    List<OficioNotificacion> buscarPorFolio(String folio);
}