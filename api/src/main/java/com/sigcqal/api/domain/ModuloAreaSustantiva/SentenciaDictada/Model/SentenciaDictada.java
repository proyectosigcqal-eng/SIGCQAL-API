package com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaDictada.Model;

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
public class SentenciaDictada {

    private Integer idSentencia;
    private Integer idAudienciaCelebrada;
    private LocalDate fechaDictado;
    private LocalDate fechaNotificacionSentencia;
    private String sentidoFallo;
    private String puntosResolutivos;
    private String numeroOficioSentencia;
    private String rutaArchivoSentencia;
    private LocalDateTime fechaRegistro;
}
