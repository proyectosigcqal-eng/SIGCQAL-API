package com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.exception;

public abstract class AlertaSeguimientoException extends RuntimeException {
    private final String codigo;

    protected AlertaSeguimientoException(String codigo, String message) {
        super(message);
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}

