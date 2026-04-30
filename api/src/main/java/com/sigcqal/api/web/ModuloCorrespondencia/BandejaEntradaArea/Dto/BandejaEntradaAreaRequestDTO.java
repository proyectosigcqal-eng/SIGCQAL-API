package com.sigcqal.api.web.ModuloCorrespondencia.BandejaEntradaArea.Dto;

import lombok.Data;

@Data
public class BandejaEntradaAreaRequestDTO {
    private Long idCorrespondencia;
    private Long idArea;
    private Long idUsuarioAsignado;
    private String observaciones;
}

