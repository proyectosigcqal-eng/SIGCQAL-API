package com.sigcqal.api.domain.ModuloAreaSustantiva.OficioNotificacion.Model;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OficioNotificacion {
    private Long          id;
    private String        folioExpediente;
    private Integer       idAutoridad;
    private String        nombreAutoridad;    
    private Integer       idContribuyente;
    private String        nombreContribuyente; 
    private String        numOficio;
    private String        fechaAcuerdo;
    private String        fundamento;
    private String        inicialesAsesor;
    private String        tipoAcuerdo;
    private String        rutaPdf;
    private LocalDateTime fechaGeneracion;
}
