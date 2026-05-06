package com.sigcqal.api.domain.ModuloCorrespondencia.Oficio.Model;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Oficio {
    
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
    private LocalDateTime fechaEmision;
    private String nombreArea;

    private String asunto;          
    private String numeroOficio;    
    private String nombrePlantilla; 
    
    private String nombreUsuarioEmisor;
    private String areaUsuarioEmisor;
    
    private String nombreUsuarioFirmante;
    private String areaUsuarioFirmante;

    // Campos de Correspondencia
    private String dependenciaRemitente;
    private String nombreRemitente;
    private String folioUnicoCorrespondencia;
    private String asuntoCorrespondenciaCompleto;
}
