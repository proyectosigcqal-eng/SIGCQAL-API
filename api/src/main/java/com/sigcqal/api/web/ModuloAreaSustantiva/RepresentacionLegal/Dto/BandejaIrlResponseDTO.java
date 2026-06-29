package com.sigcqal.api.web.ModuloAreaSustantiva.RepresentacionLegal.Dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BandejaIrlResponseDTO {

    private Integer id;
    private String folioGobierno;
    private String contribuyente;
    private String asesor;
    private String municipio;
    private String estatus;
    private LocalDateTime fechaCreacion;
    private Boolean esEvolucion;
    private Integer idEstatus;
}