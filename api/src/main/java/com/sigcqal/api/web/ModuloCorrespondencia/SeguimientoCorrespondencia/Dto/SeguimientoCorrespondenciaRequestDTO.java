package com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoCorrespondencia.Dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SeguimientoCorrespondenciaRequestDTO {
    private Integer idSeguimientoCorrespondencia;
    private Integer idCorrespondencia;
    private String folioRespuesta;
    private String respuestaSeguimientoCorrespondencia;
    private String fechaResolucion;
    private String horaResolucion;
    
    private MultipartFile archivoAdjunto; 
    
    private Integer idUsuario;
    private Integer idEstatus;
}   