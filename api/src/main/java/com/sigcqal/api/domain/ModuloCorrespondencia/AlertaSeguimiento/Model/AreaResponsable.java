package com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model;

public class AreaResponsable {
    private Long idArea;
    private String nombreArea;
    private Long idUsuarioResponsable;

    public AreaResponsable() {}

    public AreaResponsable(Long idArea, String nombreArea, Long idUsuarioResponsable) {
        this.idArea = idArea;
        this.nombreArea = nombreArea;
        this.idUsuarioResponsable = idUsuarioResponsable;
    }

    public Long getIdArea() {
        return idArea;
    }

    public void setIdArea(Long idArea) {
        this.idArea = idArea;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    public Long getIdUsuarioResponsable() {
        return idUsuarioResponsable;
    }

    public void setIdUsuarioResponsable(Long idUsuarioResponsable) {
        this.idUsuarioResponsable = idUsuarioResponsable;
    }
}

