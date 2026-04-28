package com.sigcqal.api.web.ModuloCorrespondencia.ReasignacionCorrespondencia.Dto;

import lombok.Data;

@Data
public class ReasignacionCorrespondenciaResponseDTO {

    private Long idAcuseCorrespondencia;
    private Long idCorrespondencia;
    private Long idUsuarioRevisor;

    private String fechaAceptacion;
    private String horaAceptacion;
    private Boolean esDelArea;
    private Boolean estatusRechazado;

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
