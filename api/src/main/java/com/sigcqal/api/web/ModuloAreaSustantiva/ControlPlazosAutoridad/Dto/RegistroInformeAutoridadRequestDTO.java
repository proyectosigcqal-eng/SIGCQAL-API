package com.sigcqal.api.web.ModuloAreaSustantiva.ControlPlazosAutoridad.Dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RegistroInformeAutoridadRequestDTO {
    private String numeroOficioRespuesta;
    private Integer fojas;
    private LocalDate fechaRecepcion;
}

