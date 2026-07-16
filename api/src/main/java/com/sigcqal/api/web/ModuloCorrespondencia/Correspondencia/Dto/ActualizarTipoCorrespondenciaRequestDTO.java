package com.sigcqal.api.web.ModuloCorrespondencia.Correspondencia.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ActualizarTipoCorrespondenciaRequestDTO {

    @NotNull(message = "El id del tipo de correspondencia es obligatorio")
    @Positive(message = "El id del tipo de correspondencia debe ser mayor a 0")
    private Integer idTipoCorrespondencia;
}