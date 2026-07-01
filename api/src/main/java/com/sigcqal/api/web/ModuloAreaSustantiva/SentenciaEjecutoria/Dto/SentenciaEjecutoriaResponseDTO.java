package com.sigcqal.api.web.ModuloAreaSustantiva.SentenciaEjecutoria.Dto;

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
public class SentenciaEjecutoriaResponseDTO {

    private Integer idSentenciaEjecutoria;
    private Integer idSentencia;
    private Integer idRecursoRevision;
    private String numeroOficioEjecutoria;
    private LocalDate fechaDeclaracionEjecutoria;
    private String requerimientoCumplimiento;
    private String rutaPdfOficio;
    private LocalDateTime fechaRegistro;
    private EncabezadoHitoAmparoDto encabezado;
}
