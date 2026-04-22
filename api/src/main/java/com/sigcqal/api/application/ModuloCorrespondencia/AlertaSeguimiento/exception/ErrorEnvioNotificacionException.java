package com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.exception;

public class ErrorEnvioNotificacionException extends AlertaSeguimientoException {
    public ErrorEnvioNotificacionException(String message) {
        super("FE-03", message);
    }
}

