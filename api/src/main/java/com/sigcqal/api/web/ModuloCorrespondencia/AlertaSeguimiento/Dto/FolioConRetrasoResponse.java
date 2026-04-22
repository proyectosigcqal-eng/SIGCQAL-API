package com.sigcqal.api.web.ModuloCorrespondencia.AlertaSeguimiento.Dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class FolioConRetrasoResponse {
    private Long idFolio;
    private String folioUnico;
    private String numOficio;
    private String remitente;
    private String areaResponsable;
    private String estatus;
    private LocalDate fechaRegistro;
    private Long diasAtraso;
}

