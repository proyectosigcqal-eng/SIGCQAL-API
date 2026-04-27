package com.sigcqal.api.web.ModuloCorrespondencia.AcuseCorrespondencia.Dto;

import lombok.Data;

@Data
public class AcuseCorrespondenciaResponseDTO {

    private Long idAcuseCorrespondencia;
    private Long idCorrespondencia;
    private Long idUsuarioRevisor;

    private String fechaAceptacion;
    private String horaAceptacion;
    private Boolean esDelArea;

    private String folioUnico;
    private String numeroOficio;
    private String dependenciaRemitente;
    private String titularDependencia;
    private String asunto;
    private String fechaExpedicion;
    private String fechaRecibido;
    private String observaciones;
    private Long idArea;
}