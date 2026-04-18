package com.sigcqal.api.domain.ModuloCorrespondencia.Correspondencia.Model;

import java.time.LocalDate;

public class Correspondencia {
    private Long id;
    private Long consecutivo;
    private String folioUnico;
    private String numeroOficio;
    private LocalDate fechaExpedicion;
    private String dependenciaRemitente;
    private String titularDependencia;
    private String asunto;
    private LocalDate fechaRecibido;
    private Long idEstatus;
    private Long idUsuarioCaptura;
    private String observaciones;

    public Correspondencia() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Long consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getFolioUnico() {
        return folioUnico;
    }

    public void setFolioUnico(String folioUnico) {
        this.folioUnico = folioUnico;
    }

    public String getNumeroOficio() {
        return numeroOficio;
    }

    public void setNumeroOficio(String numeroOficio) {
        this.numeroOficio = numeroOficio;
    }

    public LocalDate getFechaExpedicion() {
        return fechaExpedicion;
    }

    public void setFechaExpedicion(LocalDate fechaExpedicion) {
        this.fechaExpedicion = fechaExpedicion;
    }

    public String getDependenciaRemitente() {
        return dependenciaRemitente;
    }

    public void setDependenciaRemitente(String dependenciaRemitente) {
        this.dependenciaRemitente = dependenciaRemitente;
    }

    public String getTitularDependencia() {
        return titularDependencia;
    }

    public void setTitularDependencia(String titularDependencia) {
        this.titularDependencia = titularDependencia;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public LocalDate getFechaRecibido() {
        return fechaRecibido;
    }

    public void setFechaRecibido(LocalDate fechaRecibido) {
        this.fechaRecibido = fechaRecibido;
    }

    public Long getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Long idEstatus) {
        this.idEstatus = idEstatus;
    }

    public Long getIdUsuarioCaptura() {
        return idUsuarioCaptura;
    }

    public void setIdUsuarioCaptura(Long idUsuarioCaptura) {
        this.idUsuarioCaptura = idUsuarioCaptura;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
