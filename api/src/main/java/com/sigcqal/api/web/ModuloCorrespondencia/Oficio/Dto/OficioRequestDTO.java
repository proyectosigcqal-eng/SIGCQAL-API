package com.sigcqal.api.web.ModuloCorrespondencia.Oficio.Dto;

import lombok.Data;

@Data
public class OficioRequestDTO {
    private Long idCorrespondencia; 
    private String instruccionSeguimiento; 
    private String observaciones; 
    private Long idUsuarioEmisor; 
    private Long idUsuarioFirmante; 
    private Long idPlantilla; 
    private Long idArea;
    private String folioUnico;
    private String urlSolicitudMemorandum;

}
