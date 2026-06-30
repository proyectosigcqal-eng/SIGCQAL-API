package com.sigcqal.api.web.ModuloAreaSustantiva.AudienciaCelebrada.Dto;

import java.time.LocalDateTime;

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
public class AudienciaCelebradaRequestDTO {

    @NotNull(message = "La audiencia en espera es obligatoria")
    private Integer idAudienciaEspera;

    private LocalDateTime fechaHoraCelebracion;

    @Size(max = 100)
    private String numeroOficioActa;

    @Size(max = 100)
    private String salaOModalidad;

    @Size(max = 255)
    private String resultadoAudiencia;

    private Boolean asistioAutoridad;

    @Size(max = 500)
    private String rutaPdfOficio;
}
