package com.sigcqal.api.web.ModuloCorrespondencia.AlertaSeguimiento.Dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AlertaEnviadaResponse {
    private Long idAlerta;
    private String folioUnico;
    private String areaDestinataria;
    private LocalDateTime fechaEnvio;
    private String mensaje;
}

