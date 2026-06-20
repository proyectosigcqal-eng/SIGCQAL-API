package com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Dto;

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
public class ResolucionFinalRequestDTO {

    @NotNull(message = "La fecha de emisión de la resolución es obligatoria")
    private LocalDate fechaEmisionResolucion;

    @Size(max = 255)
    private String conceptoCobro;

    @Size(max = 100)
    private String contactoVia;

    private Integer numeroCredito;

    @Size(max = 50)
    private String folioCredito;

    @NotNull(message = "El expediente es obligatorio")
    private Integer idExpediente;

    // ← Quitamos @NotNull — pueden no existir en todos los flujos
    private Integer idAri;
    private Integer idQuejaRespuestaAutoridad;
    private Integer idEstatusQueja;
    private Integer idEstatusExpediente;
}