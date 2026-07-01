package com.sigcqal.api.domain.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Model;

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
public class NotificacionSentenciaCumplida {

    private Integer idSentenciaCumplida;
    private Integer idSentenciaEjecutoria;
    private String numeroOficioCumplimiento;
    private String numeroOficioArchivo;
    private LocalDate fechaNotificacionArchivo;
    private String observacionesFinales;
    private String rutaPdfOficio;
    private LocalDateTime fechaRegistro;
}
