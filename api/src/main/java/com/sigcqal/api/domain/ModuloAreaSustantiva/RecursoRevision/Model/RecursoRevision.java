package com.sigcqal.api.domain.ModuloAreaSustantiva.RecursoRevision.Model;

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
public class RecursoRevision {

    private Integer idRecursoRevision;
    private Integer idSentencia;
    private String numeroOficioInterposicion;
    private String numeroExpedienteRevision;
    private String tribunalColegiadoAsig;
    private LocalDate fechaInterposicion;
    private String observacionesSeguimiento;
    private String rutaPdfOficio;
    private LocalDateTime fechaRegistro;
}
