package com.sigcqal.api.web.ModuloAreaSustantiva.ConstanciaInternaRemision.Dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerarConstanciaResponse {
    private String idConstancia;
    private String nombreArchivo;
    private String urlDescarga;
    private LocalDateTime fechaGeneracion;
    private String estatusExpediente;
    private String mensaje;
    private Boolean puedeDescargar;
}

