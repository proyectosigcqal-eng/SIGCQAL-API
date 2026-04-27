package com.sigcqal.api.web.ModuloCorrespondencia.AcuseCorrespondencia.Dto;

import lombok.Data;

@Data
public class AcuseCorrespondenciaRequestDTO {

    private Long idCorrespondencia;
    private Long idUsuarioRevisor;
    private Boolean esDelArea;
}