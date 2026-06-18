package com.sigcqal.api.web.ModuloAreaSustantiva.ContestacionAutoridad.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContestacionAutoridadResponseDTO {
    private Long id;
    private String folioExpediente;
    private String numeroOficio;
    private String nombreTitular;
    private String rutaPdfInforme;
    private String observaciones;
    private String decision;
    private String fechaRegistro;
    private String fechaOficio;
}
