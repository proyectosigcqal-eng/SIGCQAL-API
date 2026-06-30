package com.sigcqal.api.web.ModuloAreaSustantiva.AudienciaEspera.Dto;

import java.time.LocalDate;
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
public class AudienciaEsperaRequestDTO {

    @NotNull(message = "La demanda de amparo es obligatoria")
    private Integer idDemandaAmparo;

    @Size(max = 100)
    private String numeroOficioAdmision;

    private LocalDate fechaNotificacionOficio;

    private LocalDateTime fechaHoraAudienciaProg;

    @Size(max = 500)
    private String observaciones;

    @Size(max = 500)
    private String rutaPdfOficio;
}
