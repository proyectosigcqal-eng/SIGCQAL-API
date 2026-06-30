package com.sigcqal.api.web.ModuloAreaSustantiva.SentenciaDictada.Dto;

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
public class SentenciaDictadaRequestDTO {

    @NotNull(message = "La audiencia celebrada es obligatoria")
    private Integer idAudienciaCelebrada;

    private LocalDate fechaDictado;

    private LocalDate fechaNotificacionSentencia;

    @Size(max = 255)
    private String sentidoFallo;

    private String puntosResolutivos;

    @Size(max = 100)
    private String numeroOficioSentencia;

    @Size(max = 500)
    private String rutaArchivoSentencia;
}
