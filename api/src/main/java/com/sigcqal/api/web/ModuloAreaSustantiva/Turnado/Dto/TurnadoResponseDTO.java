package com.sigcqal.api.web.ModuloAreaSustantiva.Turnado.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TurnadoResponseDTO {

    @JsonProperty("folio_expediente")
    private String folioExpediente;

    @JsonProperty("id_asesor_asignado")
    private Long idAsesorAsignado;

    @JsonProperty("nombre_asesor")
    private String nombreAsesor;

    @JsonProperty("fecha_asignacion")
    private String fechaAsignacion;

    private String mensaje;
}