package com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaEjecutoria.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SentenciaEjecutoria {

    private Integer idSentenciaEjecutoria;
    private Integer idSentencia;
    private Integer idRecursoRevision;
    private String numeroOficioEjecutoria;
    private LocalDate fechaDeclaracionEjecutoria;
    private String requerimientoCumplimiento;
    private String rutaPdfOficio;
    private LocalDateTime fechaRegistro;
}
