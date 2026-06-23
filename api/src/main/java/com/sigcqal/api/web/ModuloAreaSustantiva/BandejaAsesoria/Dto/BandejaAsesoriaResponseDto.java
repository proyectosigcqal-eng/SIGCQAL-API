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
   @JsonProperty("bloqueado") private Boolean bloqueado;
    @JsonProperty("id_expediente")        private String idExpediente;
@JsonProperty("tiene_cir")            private Boolean tieneCir;
@JsonProperty("tiene_ari")            private Boolean tieneAri;
@JsonProperty("tiene_oficio")         private Boolean tieneOficio;
@JsonProperty("tiene_contestacion")   private Boolean tieneContestacion;
@JsonProperty("tiene_acci")           private Boolean tieneAcci;
@JsonProperty("tiene_resolucion")     private Boolean tieneResolucion;
@JsonProperty("checklist_completo")   private Boolean checklistCompleto;

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