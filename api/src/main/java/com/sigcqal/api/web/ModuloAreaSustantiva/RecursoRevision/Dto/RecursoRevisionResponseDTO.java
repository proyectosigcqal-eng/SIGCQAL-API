package com.sigcqal.api.web.ModuloAreaSustantiva.RecursoRevision.Dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.sigcqal.api.web.ModuloAreaSustantiva.DemandaAmparo.Dto.EncabezadoHitoAmparoDto;

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
    private String rutaPdfOficio;
    private LocalDateTime fechaRegistro;
    private EncabezadoHitoAmparoDto encabezado;
}
