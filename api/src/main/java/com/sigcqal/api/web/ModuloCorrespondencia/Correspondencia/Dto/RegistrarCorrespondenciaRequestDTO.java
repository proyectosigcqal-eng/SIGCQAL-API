package com.sigcqal.api.web.ModuloCorrespondencia.Correspondencia.Dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RegistrarCorrespondenciaRequestDTO {
    private Long id;
    //private Long consecutivo;
    private String folioUnico;
    private String numeroOficio;
    private LocalDate fechaExpedicion;
    private String dependenciaRemitente;
    private String titularDependencia;
    private String asunto;
    private LocalDate fechaRecibido;
    //private Long idEstatus;
    //private Long idUsuarioCaptura;
    //private Long idArea;
    //private String nombreArea;
    private String observaciones;
}
