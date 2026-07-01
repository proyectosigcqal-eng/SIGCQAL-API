package com.sigcqal.api.web.ModuloAreaSustantiva.BandejaRepresentacionLegal.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sigcqal.api.web.ModuloAreaSustantiva.BandejaAsesoria.Dto.UltimaModificacionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BandejaRepresentacionResponseDto {

    private String folio;

    @JsonProperty("id_expediente")
    private String idExpediente;

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

    @JsonProperty("bloqueado")
    private Boolean bloqueado;

    @JsonProperty("tiene_ficha")
    private Boolean tieneFicha;

    @JsonProperty("tiene_cir")
    private Boolean tieneCir;

    @JsonProperty("tiene_demanda")
    private Boolean tieneDemanda;

    @JsonProperty("tiene_oficio")
    private Boolean tieneOficio;

    @JsonProperty("tiene_audiencia")
    private Boolean tieneAudiencia;

    @JsonProperty("tiene_sentencia")
    private Boolean tieneSentencia;

    @JsonProperty("tiene_ejecutoria")
    private Boolean tieneEjecutoria;

    @JsonProperty("tiene_cumplimiento")
    private Boolean tieneCumplimiento;

    @JsonProperty("fecha_cir")
    private String fechaCir;

    @JsonProperty("fecha_demanda")
    private String fechaDemanda;

    @JsonProperty("fecha_oficio")
    private String fechaOficio;

    @JsonProperty("fecha_audiencia")
    private String fechaAudiencia;

    @JsonProperty("fecha_sentencia")
    private String fechaSentencia;

    @JsonProperty("fecha_ejecutoria")
    private String fechaEjecutoria;

    @JsonProperty("fecha_registro")
    private String fechaRegistro;

    private Integer idDemandaAmparo;

    @JsonProperty("semaforo")
    private String semaforo;
}
