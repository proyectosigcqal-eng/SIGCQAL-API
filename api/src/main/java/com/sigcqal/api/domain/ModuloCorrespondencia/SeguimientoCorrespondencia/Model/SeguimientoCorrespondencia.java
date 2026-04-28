package com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoCorrespondencia.Model;

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
public class SeguimientoCorrespondencia {
    private Integer idSeguimientoCorrespondencia;
    private Integer idCorrespondencia;
    private String folioRespuesta;
    private String respuestaSeguimientoCorrespondencia;
    private LocalDate fechaResolucion;
    private LocalTime horaResolucion;
    private String archivoAdjunto;
    private Integer idUsuario;
    private Integer idEstatus;
    private LocalDateTime fechaRegistro;
}
