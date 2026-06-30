package com.sigcqal.api.web.ModuloAreaSustantiva.RecursoRevision.Dto;

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
public class RecursoRevisionRequestDTO {

    @NotNull(message = "La sentencia dictada es obligatoria")
    private Integer idSentencia;

    @Size(max = 100)
    private String numeroOficioInterposicion;

    @Size(max = 100)
    private String numeroExpedienteRevision;

    @Size(max = 255)
    private String tribunalColegiadoAsig;

    private LocalDate fechaInterposicion;

    @Size(max = 500)
    private String observacionesSeguimiento;

    @Size(max = 500)
    private String rutaPdfOficio;
}
