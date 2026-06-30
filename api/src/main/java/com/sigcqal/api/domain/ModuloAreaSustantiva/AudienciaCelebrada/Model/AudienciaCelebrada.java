package com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaCelebrada.Model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AudienciaCelebrada {

    private Integer idAudienciaCelebrada;
    private Integer idAudienciaEspera;
    private LocalDateTime fechaHoraCelebracion;
    private String numeroOficioActa;
    private String salaOModalidad;
    private String resultadoAudiencia;
    private Boolean asistioAutoridad;
    private LocalDateTime fechaRegistro;
}
