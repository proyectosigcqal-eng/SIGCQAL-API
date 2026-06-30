package com.sigcqal.api.web.ModuloAreaSustantiva.SentenciaEjecutoria.Dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SentenciaEjecutoriaRequestDTO {

    @NotNull(message = "La sentencia dictada es obligatoria")
    private Integer idSentencia;

    private Integer idRecursoRevision;

    @Size(max = 100)
    private String numeroOficioEjecutoria;

    private LocalDate fechaDeclaracionEjecutoria;

    @Size(max = 500)
    private String requerimientoCumplimiento;
}
