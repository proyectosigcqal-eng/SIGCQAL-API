package com.sigcqal.api.domain.ModuloCorrespondencia.Memorandum.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Memorandum {
    
private Long id; 
    private Long idUsuarioEmisor;   
    private Long idUsuarioFirmante; 
    private Long idCorrespondencia; 
    private Long idPlantilla;       
    private Long idArea;
    
    private String folioUnico; 
    private String instruccionSeguimiento; 
    private String observaciones;
    private String urlSolicitudMemorandum;
    private String nombreArea;

    private String asunto;          
    private String numeroOficio;    
    private String nombrePlantilla; 
    
    private String nombreUsuarioEmisor;
    private String areaUsuarioEmisor;
    
    private String nombreUsuarioFirmante;
    private String areaUsuarioFirmante;



}
