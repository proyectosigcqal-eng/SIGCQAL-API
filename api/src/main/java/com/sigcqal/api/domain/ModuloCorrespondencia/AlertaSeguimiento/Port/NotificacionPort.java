package com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Port;

public interface NotificacionPort {
    void enviarNotificacionAArea(Long idAreaDestino, String asunto, String mensaje, Long idFolio);
}

