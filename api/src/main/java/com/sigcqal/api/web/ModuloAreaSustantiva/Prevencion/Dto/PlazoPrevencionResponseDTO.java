package com.sigcqal.api.web.ModuloAreaSustantiva.Prevencion.Dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlazoPrevencionResponseDTO {

    @JsonProperty("folio_expediente")
    private String folioExpediente;

    @JsonProperty("fecha_inicio")
    private String fechaInicio;

    @JsonProperty("fecha_limite")
    private String fechaLimite;

    @JsonProperty("dias_habiles_restantes")
    private Integer diasHabilesRestantes;

    @JsonProperty("semaforo_estado")
    private String semaforoEstado;

    private Boolean vencido;
}