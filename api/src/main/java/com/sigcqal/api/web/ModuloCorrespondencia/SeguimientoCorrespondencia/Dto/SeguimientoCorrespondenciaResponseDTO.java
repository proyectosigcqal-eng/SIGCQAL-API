package com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoCorrespondencia.Dto;

import lombok.Data;

@Data
public class SeguimientoCorrespondenciaResponseDTO {
    private Integer idSeguimientoCorrespondencia;
    private Integer idCorrespondencia;
    private String folioRespuesta;
    private String respuestaSeguimientoCorrespondencia;
    private String fechaResolucion;
    private String horaResolucion;
    private String archivoAdjunto;
    private Integer idUsuario;
    private Integer idEstatus;
    private String fechaRegistro;
}
