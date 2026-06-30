package com.sigcqal.api.web.ModuloAreaSustantiva.AudienciaEspera.Dto;

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
public class AudienciaEsperaResponseDTO {

    private Integer idAudienciaEspera;
    private Integer idDemandaAmparo;
    private String numeroOficioAdmision;
    private LocalDate fechaNotificacionOficio;
    private LocalDateTime fechaHoraAudienciaProg;
    private String observaciones;
    private String rutaPdfOficio;
    private LocalDateTime fechaRegistro;
    private EncabezadoHitoAmparoDto encabezado;
}
