package com.sigcqal.api.web.ModuloAreaSustantiva.ControlPlazosAutoridad.Dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SemaforoAutoridadResponseDTO {
    private Long expedienteId;
    private String folioGobierno;
    private SemaforoEstadoEnum estado;
    private Integer diasHabilesRestantes;
    private LocalDateTime fechaEnvioOficioAutoridad;
    private LocalDate fechaLimiteInforme;
    private Boolean vencido;
}

