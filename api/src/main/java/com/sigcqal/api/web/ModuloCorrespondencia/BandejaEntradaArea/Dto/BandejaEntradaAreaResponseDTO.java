package com.sigcqal.api.web.ModuloCorrespondencia.BandejaEntradaArea.Dto;

import lombok.Data;

@Data
public class BandejaEntradaAreaResponseDTO {
    private Long id;
    private Long idCorrespondencia;
    private Long idArea;
    private Long idUsuarioAsignado;
    private String estatus;
    private String fechaAsignacion;
    private String observaciones;
    private String nombreArea;
    private String folioCorrespondencia;
    private String asuntoCorrespondencia;
}

