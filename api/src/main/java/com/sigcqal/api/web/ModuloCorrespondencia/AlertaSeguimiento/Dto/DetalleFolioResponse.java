package com.sigcqal.api.web.ModuloCorrespondencia.AlertaSeguimiento.Dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DetalleFolioResponse {
    private Long idFolio;
    private String folioUnico;
    private String numOficioExterno;
    private String dependenciaRemitente;
    private String nombreRemitente;
    private Long idAreaResponsable;
    private String nombreAreaResponsable;
    private String estatusActual;
    private LocalDate fechaRegistro;
    private Long diasAtraso;
}

