package com.sigcqal.api.application.ModuloCorrespondencia.AlertaSeguimiento.exception;

public class FolioYaConcluidoException extends AlertaSeguimientoException {
    public FolioYaConcluidoException(String message) {
        super("FE-02", message);
    }
}

