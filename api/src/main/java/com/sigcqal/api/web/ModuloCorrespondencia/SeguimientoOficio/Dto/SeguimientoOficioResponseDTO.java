package com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoOficio.Dto;

import lombok.Data;

@Data
public class SeguimientoOficioResponseDTO {
    private Integer idSeguimientoOficio;
    private Integer idOficio;
    private Integer folioRespuesta;
    private String respuestasSeguimientoOficio;
    private String fechaResolucion;
    private String horaResolucion;
    private String archivoAdjunto;
    private Integer idUsuario;
    private Integer idEstatus;
    private String fechaRegistro;
}