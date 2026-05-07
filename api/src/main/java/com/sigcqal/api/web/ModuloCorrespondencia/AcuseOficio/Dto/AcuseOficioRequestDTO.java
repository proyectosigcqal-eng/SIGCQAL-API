package com.sigcqal.api.web.ModuloCorrespondencia.AcuseOficio.Dto;

import lombok.Data;

@Data
public class AcuseOficioRequestDTO {
    private Long idOficio;
    private Long idUsuarioRevisor;
    private Boolean esDelArea;
}