package com.sigcqal.api.web.ModuloAreaSustantiva.ConstanciaInternaRemision.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenerarConstanciaRequest {
    @NotBlank(message = "Documentación que se remite es requerida")
    private String documentacionRemite;

    @NotBlank(message = "Motivos por los que se remite es requerido")
    private String motivosRemite;

    private String observaciones;
    private String asesorQueRemite;
    private String nombreEncargado;
    private String fechaCIR;
    private String ipCliente;
}

