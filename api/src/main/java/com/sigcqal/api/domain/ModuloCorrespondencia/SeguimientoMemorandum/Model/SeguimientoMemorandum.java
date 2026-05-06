package com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoMemorandum.Model;

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
public class SeguimientoMemorandum {
    private Long idSeguimientoMemorandum;
    private Long idMemo;

    /**
     * Generado por la base de datos (serial4). No debe enviarse desde el cliente.
     */
    private Integer folioRespuesta;

    private String respuestaSeguimientoMemorandum;
    private LocalDate fechaResolucion;
    private LocalTime horaResolucion;
    private String archivoAdjunto;
    private String folioFormateado;

    private Long idUsuario;
    private Long idEstatus;

    private LocalDateTime fechaRegistro;
}

