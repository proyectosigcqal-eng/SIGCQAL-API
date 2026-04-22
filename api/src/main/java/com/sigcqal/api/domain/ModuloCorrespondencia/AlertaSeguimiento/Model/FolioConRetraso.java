package com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model;

import java.time.LocalDate;

public class FolioConRetraso {
    private Long idFolio;
    private String folioUnico;
    private String numOficioExterno;
    private String dependenciaRemitente;
    private String nombreRemitente;
    private String nombreAreaResponsable;
    private Long idAreaResponsable;
    private String nombreEstatus;
    private LocalDate fechaRegistro;
    private Long diasAtraso;

    public FolioConRetraso() {}

    public FolioConRetraso(
            Long idFolio,
            String folioUnico,
            String numOficioExterno,
            String dependenciaRemitente,
            String nombreRemitente,
            String nombreAreaResponsable,
            Long idAreaResponsable,
            String nombreEstatus,
            LocalDate fechaRegistro,
            Long diasAtraso
    ) {
        this.idFolio = idFolio;
        this.folioUnico = folioUnico;
        this.numOficioExterno = numOficioExterno;
        this.dependenciaRemitente = dependenciaRemitente;
        this.nombreRemitente = nombreRemitente;
        this.nombreAreaResponsable = nombreAreaResponsable;
        this.idAreaResponsable = idAreaResponsable;
        this.nombreEstatus = nombreEstatus;
        this.fechaRegistro = fechaRegistro;
        this.diasAtraso = diasAtraso;
    }

    public Long getIdFolio() {
        return idFolio;
    }

    public void setIdFolio(Long idFolio) {
        this.idFolio = idFolio;
    }

    public String getFolioUnico() {
        return folioUnico;
    }

    public void setFolioUnico(String folioUnico) {
        this.folioUnico = folioUnico;
    }

    public String getNumOficioExterno() {
        return numOficioExterno;
    }

    public void setNumOficioExterno(String numOficioExterno) {
        this.numOficioExterno = numOficioExterno;
    }

    public String getDependenciaRemitente() {
        return dependenciaRemitente;
    }

    public void setDependenciaRemitente(String dependenciaRemitente) {
        this.dependenciaRemitente = dependenciaRemitente;
    }

    public String getNombreRemitente() {
        return nombreRemitente;
    }

    public void setNombreRemitente(String nombreRemitente) {
        this.nombreRemitente = nombreRemitente;
    }

    public String getNombreAreaResponsable() {
        return nombreAreaResponsable;
    }

    public void setNombreAreaResponsable(String nombreAreaResponsable) {
        this.nombreAreaResponsable = nombreAreaResponsable;
    }

    public Long getIdAreaResponsable() {
        return idAreaResponsable;
    }

    public void setIdAreaResponsable(Long idAreaResponsable) {
        this.idAreaResponsable = idAreaResponsable;
    }

    public String getNombreEstatus() {
        return nombreEstatus;
    }

    public void setNombreEstatus(String nombreEstatus) {
        this.nombreEstatus = nombreEstatus;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Long getDiasAtraso() {
        return diasAtraso;
    }

    public void setDiasAtraso(Long diasAtraso) {
        this.diasAtraso = diasAtraso;
    }
}

