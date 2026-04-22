package com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.exception;

public class FolioSinAreaAsignadaException extends AlertaSeguimientoException {
    public FolioSinAreaAsignadaException(String message) {
        super("FE-01", message);
    }
}

