package com.sigcqal.api.web.ModuloAreaSustantiva.ConstanciaInternaRemision.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerarConstanciaRequest {
    private Integer expedienteId;
    private String analisisJuridico;
    private String determinacion;
    private String ipCliente;
}

