package com.sigcqal.api.web.ModuloAreaSustantiva.BandejaAsesoria.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BandejaAsesoriaResponseDto {
    private String folio;

    @JsonProperty("municipio_procedencia")
    private String municipioProcedencia;

    private String contribuyente;

    @JsonProperty("tipo_acto")
    private String tipoActo;

    @JsonProperty("estatus_principal")
    private String estatusPrincipal;

    @JsonProperty("estatus_secundario")
    private String estatusSecundario;

    @JsonProperty("ultima_modificacion")
    private UltimaModificacionDto ultimaModificacion;

    @JsonProperty("tiene_bitacora")
    private Boolean tieneBitacora;

    @JsonProperty("tiene_ficha")
    private Boolean tieneFicha;
}
