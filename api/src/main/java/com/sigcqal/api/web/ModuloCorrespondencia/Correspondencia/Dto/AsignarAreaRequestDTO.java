package com.sigcqal.api.web.ModuloCorrespondencia.Correspondencia.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AsignarAreaRequestDTO {
    @NotNull
    private Long idArea;
}

