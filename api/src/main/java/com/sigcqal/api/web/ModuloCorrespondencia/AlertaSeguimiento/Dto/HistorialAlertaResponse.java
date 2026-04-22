package com.sigcqal.api.web.ModuloCorrespondencia.AlertaSeguimiento.Dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class HistorialAlertaResponse {
    private Long idAlerta;
    private String usuarioEmisor;
    private String areaDestinataria;
    private String mensajeUrgencia;
    private LocalDateTime fechaEnvio;
    private Long diasAtrasoAlMomento;
}

