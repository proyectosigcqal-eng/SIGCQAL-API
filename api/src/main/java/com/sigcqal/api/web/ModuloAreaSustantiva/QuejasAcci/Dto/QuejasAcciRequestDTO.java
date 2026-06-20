package com.sigcqal.api.web.ModuloAreaSustantiva.QuejasAcci.Dto;


import lombok.Data;

@Data
public class QuejasAcciRequestDTO {
    private Long   idQueja;
    private Long   idOficioAutoridad;
    private String folioExpediente;
    private String contribuyente;
    private String dependencia;
    private String numOficioRecibido;
    private String fechaOficio;
    private String fechaRecepcion;
    private String encargadoDependencia;
    private String fechaProveido;
    private String documentosAnexos;
    private String titularRequerido;
    private String motivosRequerimiento;
    private String inicialesAsesor;
}