package com.sigcqal.api.web.ModuloCorrespondencia.Correspondencia.Dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RegistrarCorrespondenciaRequestDTO {
    @NotBlank
    private String numeroOficio;

    @NotNull
    private LocalDate fechaExpedicion;

    @NotBlank
    private String dependenciaRemitente;

    @NotBlank
    private String titularDependencia;

    @NotBlank
    private String asunto;

    @NotNull
    private LocalDate fechaRecibido;

    @NotNull
    @Positive
    private Long idUsuarioCaptura;
}
