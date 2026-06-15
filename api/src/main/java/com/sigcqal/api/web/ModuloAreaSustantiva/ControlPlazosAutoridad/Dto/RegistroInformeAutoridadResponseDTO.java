package com.sigcqal.api.web.ModuloAreaSustantiva.ControlPlazosAutoridad.Dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistroInformeAutoridadResponseDTO {
    private Long expedienteId;
    private String folioGobierno;
    private String numeroOficioRespuesta;
    private Integer fojas;
    private LocalDateTime fechaRecepcionInforme;
    private String rutaPdfInforme;
    private String estadoAlerta5Dias;
}

