package com.sigcqal.api.web.ModuloAreaSustantiva.IrlDemandaAmparo.Dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class IrlDemandaAmparoRequestDTO {
    private Integer    idExpediente;
    private Integer    idRepresentacionLegal;
    private String folioExpediente; 
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
    private String     argumentacionFaltaNotificacion;
    private String     transcripcionLeyIngresos;
    private String folioReciboPago2;
private String numRecibo1;
private String numRecibo2;
private String domicilioAutoridad;

}

