package com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.exception;

public class MensajeAlertaInvalidoException extends AlertaSeguimientoException {
    public MensajeAlertaInvalidoException(String message) {
        super("FE-04", message);
    }
}

