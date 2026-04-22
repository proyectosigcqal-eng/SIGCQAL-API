package com.sigcqal.api.web.ModuloCorrespondencia.AlertaSeguimiento.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EnviarAlertaRequest {
    @NotNull
    private Long idFolio;

    @Size(max = 500)
    private String mensajeUrgencia;
}

