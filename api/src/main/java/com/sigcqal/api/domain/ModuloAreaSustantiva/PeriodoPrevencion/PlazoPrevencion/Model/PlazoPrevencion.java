package com.sigcqal.api.domain.ModuloAreaSustantiva.PeriodoPrevencion.PlazoPrevencion.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlazoPrevencion {
    private String folioExpediente;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaLimite;
    private Integer diasHabilesRestantes;
    private String semaforoEstado;   
    private Boolean vencido;
}