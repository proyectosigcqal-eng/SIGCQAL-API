package com.sigcqal.api.domain.ModuloCorrespondencia.AlertaSeguimiento.Model;

import java.time.LocalDateTime;

public class AlertaSeguimiento {
    private Long idAlerta;
    private Long idFolio;
    private String folioUnico;
    private Long idUsuarioEmisor;
    private String nombreUsuarioEmisor;
    private Long idAreaDestinataria;
    private String nombreAreaDestinataria;
    private String mensajeUrgencia;
    private LocalDateTime fechaEnvio;
    private Long diasAtraso;

    public AlertaSeguimiento() {}

    public Long getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(Long idAlerta) {
        this.idAlerta = idAlerta;
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

    public Long getIdUsuarioEmisor() {
        return idUsuarioEmisor;
    }

    public void setIdUsuarioEmisor(Long idUsuarioEmisor) {
        this.idUsuarioEmisor = idUsuarioEmisor;
    }

    public String getNombreUsuarioEmisor() {
        return nombreUsuarioEmisor;
    }

    public void setNombreUsuarioEmisor(String nombreUsuarioEmisor) {
        this.nombreUsuarioEmisor = nombreUsuarioEmisor;
    }

    public Long getIdAreaDestinataria() {
        return idAreaDestinataria;
    }

    public void setIdAreaDestinataria(Long idAreaDestinataria) {
        this.idAreaDestinataria = idAreaDestinataria;
    }

    public String getNombreAreaDestinataria() {
        return nombreAreaDestinataria;
    }

    public void setNombreAreaDestinataria(String nombreAreaDestinataria) {
        this.nombreAreaDestinataria = nombreAreaDestinataria;
    }

    public String getMensajeUrgencia() {
        return mensajeUrgencia;
    }

    public void setMensajeUrgencia(String mensajeUrgencia) {
        this.mensajeUrgencia = mensajeUrgencia;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public Long getDiasAtraso() {
        return diasAtraso;
    }

    public void setDiasAtraso(Long diasAtraso) {
        this.diasAtraso = diasAtraso;
    }
}

