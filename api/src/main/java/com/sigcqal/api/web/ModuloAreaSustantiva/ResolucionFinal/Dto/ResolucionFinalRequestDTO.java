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

    @Size(max = 255, message = "El concepto de cobro no puede exceder 255 caracteres")
    private String conceptoCobro;

    @Size(max = 100, message = "El contacto vía no puede exceder 100 caracteres")
    private String contactoVia;

    private Integer numeroCredito;

    @Size(max = 50, message = "El folio de crédito no puede exceder 50 caracteres")
    private String folioCredito;

    @NotNull(message = "El expediente es obligatorio")
    private Integer idExpediente;

    @NotNull(message = "El ARI es obligatorio")
    private Integer idAri;

    @NotNull(message = "La respuesta de la autoridad es obligatoria")
    private Integer idQuejaRespuestaAutoridad;

    @NotNull(message = "El estatus de la queja es obligatorio")
    private Integer idEstatusQueja;

    @NotNull(message = "El estatus del expediente es obligatorio")
    private Integer idEstatusExpediente;
}