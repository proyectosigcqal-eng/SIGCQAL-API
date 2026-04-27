package com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoMemorandum.Dto;

import lombok.Data;

@Data
public class SeguimientoMemorandumResponseDTO {
    private Long idSeguimientoMemorandum;
    private Long idMemo;
    private Integer folioRespuesta;
    private String respuestaSeguimientoMemorandum;
    private String fechaResolucion;
    private String horaResolucion;
    private String archivoAdjunto;
    private Long idUsuario;
    private Long idEstatus;
    private String fechaRegistro;
}
