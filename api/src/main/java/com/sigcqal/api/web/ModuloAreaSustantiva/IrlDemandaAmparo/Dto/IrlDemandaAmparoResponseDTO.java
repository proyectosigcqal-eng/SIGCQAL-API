package com.sigcqal.api.web.ModuloAreaSustantiva.IrlDemandaAmparo.Dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class IrlDemandaAmparoResponseDTO {
    private Integer    idDemandaAmparo;
    private Integer    idExpediente;
    private Integer    idRepresentacionLegal;
    private String     autoridadReclamadaMunicipio;
    private BigDecimal superficieTerreno;
    private BigDecimal superficieConstruccion;
    private String     tipoConstruccion;
    private String     zonificacion;
    private String     folioReciboPago;
    private BigDecimal montoPago;
    private LocalDate  fechaPrimerPago;
    private Boolean    incluyeMultasHistoricas;
    private String     aniosMultasHistoricas;
    private String     rutaPdfDemandaGenerada;
    private String     rutaPdfDemandaPresentada;
    private String     rutaPdfAcuseDemanda;
    private LocalDate  fechaPresentacionDemanda;
    @JsonProperty("fecha_registro")
    private java.time.LocalDateTime fechaRegistro;
    private String numRecibo1;
private String numRecibo2;
private String domicilioAutoridad;
}