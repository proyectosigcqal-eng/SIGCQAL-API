package com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResolucionFinalResponseDTO {

    private Integer       idResolucionFinal;
    private LocalDate     fechaEmisionResolucion;
    private String        conceptoCobro;
    private String        contactoVia;
    private Integer       numeroCredito;
    private String        folioCredito;
    private Integer       idExpediente;
    private Integer       idAri;
    private Integer       idQuejaRespuestaAutoridad;
    private Integer       idEstatusQueja;
    private String        rutaResolucionFinal;
    private LocalDateTime fechaEmision;
    private Integer       idEstatusExpediente;
}