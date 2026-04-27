package com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoMemorandum.Dto;

import lombok.Data;

@Data
public class SeguimientoMemorandumRequestDTO {
    private Long idSeguimientoMemorandum;
    private Long idMemo;

    // folioRespuesta NO se recibe: lo genera la BD (serial4)

    private String respuestaSeguimientoMemorandum;
    private String fechaResolucion;
    private String horaResolucion;
    private String archivoAdjunto;

    private Long idUsuario;
    private Long idEstatus;
}

