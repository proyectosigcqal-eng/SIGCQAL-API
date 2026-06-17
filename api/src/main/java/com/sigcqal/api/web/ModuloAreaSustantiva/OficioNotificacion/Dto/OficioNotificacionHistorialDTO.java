package com.sigcqal.api.web.ModuloAreaSustantiva.OficioNotificacion.Dto;


import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class OficioNotificacionHistorialDTO {
    private Long          id;
    private String        numOficio;
    private String        nombreAutoridad;
    private String        fechaAcuerdo;
    private String        tipoAcuerdo;
    private String        rutaPdf;
    private LocalDateTime fechaGeneracion;

}