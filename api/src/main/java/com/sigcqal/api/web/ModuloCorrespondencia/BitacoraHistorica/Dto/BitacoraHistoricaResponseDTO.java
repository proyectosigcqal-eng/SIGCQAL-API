package com.sigcqal.api.web.ModuloCorrespondencia.BitacoraHistorica.Dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BitacoraHistoricaResponseDTO {
    private Long idLog; 
    private Long idCorrespondencia; 
    private Long idUsuarioAccion; 
    private Integer estatusAnterior; 
    private Integer estatusNuevo; 
    private String observaciones; 
    private String nombreUsuario;
    private String nombreEstatusAnterior;
    private String nombreEstatusNuevo;
    private String dependenciaRemitente;
    private String asuntoCorrespondencia;
    private LocalDateTime fechaMovimiento; 
}
