package com.sigcqal.api.web.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Dto;

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
public class NotificacionSentenciaCumplidaRequestDTO {

    @NotNull(message = "La sentencia ejecutoria es obligatoria")
    private Integer idSentenciaEjecutoria;

    @Size(max = 100)
    private String numeroOficioCumplimiento;

    @Size(max = 100)
    private String numeroOficioArchivo;

    private LocalDate fechaNotificacionArchivo;

    @Size(max = 500)
    private String observacionesFinales;
}
