package com.sigcqal.api.web.ModuloAreaSustantiva.IrlDemandaAmparo.Dto;

import lombok.*;
import java.time.LocalDate;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SemaforoJudicialDTO {
    private LocalDate fechaPrimerPago;
    private LocalDate fechaInicioPlazo;   // D+1
    private LocalDate fechaLimite;        // D+15 días hábiles CJF
    private Integer   diasHabilesRestantes;
    private Boolean   vencido;
    private String    color;              // VERDE | AMARILLO | ROJO | GRIS
    private String    mensaje;
}