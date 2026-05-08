package com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoOficio.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeguimientoOficio {
    private Integer idSeguimientoOficio;
    private Integer idOficio;
    private Integer folioRespuesta;
    private String respuestasSeguimientoOficio;
    private LocalDate fechaResolucion;
    private LocalTime horaResolucion;
    private String archivoAdjunto;
    private Integer idUsuario;
    private Integer idEstatus;
    private LocalDateTime fechaRegistro;
}