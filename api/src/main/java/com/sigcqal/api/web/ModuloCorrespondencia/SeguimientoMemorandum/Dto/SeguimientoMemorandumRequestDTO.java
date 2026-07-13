package com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoMemorandum.Dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class SeguimientoMemorandumRequestDTO {
    private Long idSeguimientoMemorandum;
    private Long idMemo;

    // folioRespuesta NO se recibe: lo genera la BD (serial4)

    private String respuestaSeguimientoMemorandum;
    private String fechaResolucion;
    private String horaResolucion;

    /** PDF opcional; se almacena bajo /api/files/seguimiento-memorandum/{folioFormateado}.pdf */
    private MultipartFile archivoAdjunto;

    private Long idUsuario;
    private Long idEstatus;
    private String nombreEncargado;
}
