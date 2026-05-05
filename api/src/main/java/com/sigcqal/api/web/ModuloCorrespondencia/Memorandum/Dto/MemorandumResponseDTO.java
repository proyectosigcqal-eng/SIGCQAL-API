package com.sigcqal.api.web.ModuloCorrespondencia.Memorandum.Dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MemorandumResponseDTO {
    private Long id; 
    private String folioUnico; 
    private String instruccionSeguimiento; 
    private String observaciones;
    private LocalDateTime fechaEmision;
    private Long idCorrespondencia;
    private Long idUsuarioEmisor;
    private Long idUsuarioFirmante;
    private Long idPlantilla;
    private Long idArea;
    private String nombreArea;
    private String asuntoCorrespondencia;        
    private String nombreUsuarioEmisor;   
    private String nombreUsuarioFirmante; 
    private String nombrePlantilla;
    private String urlMemorandumGenerado; 
    
    // Campos de Correspondencia
    private String dependenciaRemitente;
    private String nombreRemitente;
    private String folioUnicoCorrespondencia;
    private String asuntoCorrespondenciaCompleto;
}