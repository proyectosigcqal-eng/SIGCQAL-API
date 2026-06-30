package com.sigcqal.api.web.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Dto;

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
public class NotificacionSentenciaCumplidaResponseDTO {

    private Integer idSentenciaCumplida;
    private Integer idSentenciaEjecutoria;
    private String numeroOficioCumplimiento;
    private String numeroOficioArchivo;
    private LocalDate fechaNotificacionArchivo;
    private String observacionesFinales;
    private LocalDateTime fechaRegistro;
}
