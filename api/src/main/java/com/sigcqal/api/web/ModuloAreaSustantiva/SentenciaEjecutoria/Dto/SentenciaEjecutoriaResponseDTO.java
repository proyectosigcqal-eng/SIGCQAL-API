package com.sigcqal.api.web.ModuloAreaSustantiva.SentenciaEjecutoria.Dto;

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
public class SentenciaEjecutoriaResponseDTO {

    private Integer idSentenciaEjecutoria;
    private Integer idSentencia;
    private Integer idRecursoRevision;
    private String numeroOficioEjecutoria;
    private LocalDate fechaDeclaracionEjecutoria;
    private String requerimientoCumplimiento;
    private LocalDateTime fechaRegistro;
}
