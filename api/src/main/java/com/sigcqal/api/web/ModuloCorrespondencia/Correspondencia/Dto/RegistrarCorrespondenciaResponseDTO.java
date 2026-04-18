package com.sigcqal.api.web.ModuloCorrespondencia.Correspondencia.Dto;

import lombok.Data;

@Data
public class RegistrarCorrespondenciaResponseDTO {
    private Long idCorrespondencia;
    private Long consecutivo;
    private String folioUnico;
    private String mensaje;
}
