package com.sigcqal.api.web.ModuloAreaSustantiva.ConstanciaInternaRemision.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConstanciaPreviewResponse {
    private Integer expedienteId;
    private String folioAsesoria;
    private String nombreQuejoso;
    private String asunto;
    private String autoridadResponsable;
    private String fechaGeneracion;
    private String numeroConstancia;
    private String asesorEmisor;
    private Boolean puedeGenerar;
    private String motivoBloqueo;
}
