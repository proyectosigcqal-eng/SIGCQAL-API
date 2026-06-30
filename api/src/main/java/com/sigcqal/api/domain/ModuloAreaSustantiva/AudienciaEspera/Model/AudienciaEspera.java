package com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaEspera.Model;

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
public class AudienciaEspera {

    private Integer idAudienciaEspera;
    private Integer idDemandaAmparo;
    private String numeroOficioAdmision;
    private LocalDate fechaNotificacionOficio;
    private LocalDateTime fechaHoraAudienciaProg;
    private String observaciones;
    private LocalDateTime fechaRegistro;
}
