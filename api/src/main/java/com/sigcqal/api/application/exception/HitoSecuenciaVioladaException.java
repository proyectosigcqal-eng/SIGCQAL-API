package com.sigcqal.api.application.exception;

public class HitoSecuenciaVioladaException extends RuntimeException {

    private final String hitoActual;
    private final String hitoPredecesor;
    private final Integer idPredecesor;

    public HitoSecuenciaVioladaException(String hitoActual, String hitoPredecesor, Integer idPredecesor) {
        super(String.format(
                "No se puede registrar '%s': el hito predecesor '%s' con id %d no existe en el sistema.",
                hitoActual, hitoPredecesor, idPredecesor));
        this.hitoActual = hitoActual;
        this.hitoPredecesor = hitoPredecesor;
        this.idPredecesor = idPredecesor;
    }

    public String getHitoActual() {
        return hitoActual;
    }

    public String getHitoPredecesor() {
        return hitoPredecesor;
    }

    public Integer getIdPredecesor() {
        return idPredecesor;
    }
}
