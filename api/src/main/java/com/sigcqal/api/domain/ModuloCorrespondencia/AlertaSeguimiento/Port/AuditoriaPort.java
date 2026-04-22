package com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Port;

public interface AuditoriaPort {
    void registrarEvento(Long idFolio, Long idUsuarioAccion, String estadoAnterior, String estadoNuevo, String observaciones);
}

