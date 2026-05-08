package com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoOficio.Dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SeguimientoOficioRequestDTO {
    private Integer idSeguimientoOficio;
    private Integer idOficio;
    private Integer folioRespuesta;
    private String respuestasSeguimientoOficio;
    private String fechaResolucion;
    private String horaResolucion;
    private MultipartFile archivoAdjunto;
    private Integer idUsuario;
    private Integer idEstatus;
}