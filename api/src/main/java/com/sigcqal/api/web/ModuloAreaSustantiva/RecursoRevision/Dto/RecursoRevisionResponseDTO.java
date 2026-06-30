package com.sigcqal.api.web.ModuloAreaSustantiva.RecursoRevision.Dto;

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
public class RecursoRevisionResponseDTO {

    private Integer idRecursoRevision;
    private Integer idSentencia;
    private String numeroOficioInterposicion;
    private String numeroExpedienteRevision;
    private String tribunalColegiadoAsig;
    private LocalDate fechaInterposicion;
    private String observacionesSeguimiento;
    private LocalDateTime fechaRegistro;
}
