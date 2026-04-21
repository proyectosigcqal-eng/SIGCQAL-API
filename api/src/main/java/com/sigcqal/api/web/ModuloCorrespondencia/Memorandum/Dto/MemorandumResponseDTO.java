package com.sigcqal.api.web.ModuloCorrespondencia.Memorandum.Dto;

import lombok.Data;

@Data
public class MemorandumResponseDTO {
    private Long id; 
    private String folioUnico; 
    private String instruccionSeguimiento; 
    private String observaciones;
    private Long idCorrespondencia;
    private Long idUsuarioEmisor;
    private Long idUsuarioFirmante;
    private Long idPlantilla;
    private Long idArea;
    private String asuntoCorrespondencia;        
    private String nombreUsuarioEmisor;   
    private String nombreUsuarioFirmante; 
    private String nombrePlantilla;       
}