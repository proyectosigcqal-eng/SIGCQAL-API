package com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Dto;


import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResolucionFinalDatosPreviosDTO {
    private Integer idExpediente;
    private Integer idAri;
    private Integer idQuejaRespuestaAutoridad;
    private Integer idEstatusQueja;
    private Integer idEstatusExpediente;
    private LocalDate fechaSolicitud;
    private String numeroOficio;
    private LocalDate fechaOficio;
}