package com.sigcqal.api.domain.ModuloAreaSustantiva.ContestacionAutoridad.Model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ContestacionAutoridad {
    private Long id;
    private Integer idQueja;
    private String folioExpediente;
    private String numeroOficio;
    private Integer idAutoridad;
    private String nombreTitular;
    private String rutaPdfInforme;
    private String observaciones;
    private String decision;
    private LocalDateTime fechaRegistro;
}