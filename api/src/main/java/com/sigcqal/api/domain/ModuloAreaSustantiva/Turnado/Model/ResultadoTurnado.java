package com.sigcqal.api.domain.ModuloAreaSustantiva.Turnado.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoTurnado {
    private String        folioExpediente;
    private Long          idAsesorAsignado;
    private String        nombreAsesor;
    private LocalDateTime fechaAsignacion;
    private Long          idAsesorAnterior;
}