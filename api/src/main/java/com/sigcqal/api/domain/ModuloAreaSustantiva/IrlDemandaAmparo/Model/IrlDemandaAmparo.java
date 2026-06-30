package com.sigcqal.api.domain.ModuloAreaSustantiva.IrlDemandaAmparo.Model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;

@Data @Builder(toBuilder = true) @NoArgsConstructor @AllArgsConstructor 
public class IrlDemandaAmparo {
    private Integer idDemandaAmparo;
    private Integer idExpediente;
    private Integer idRepresentacionLegal;
    private Integer idRlCir;
private Integer idQuejaRlCir;

    private String nombreQuejoso;
    private String calleQuejoso;
    private String coloniaQuejoso;
private String numCalleQuejoso;
private String cpQuejoso;
private String folioReciboPago2;

private String numRecibo1;


private String numRecibo2;




private String domicilioAutoridad;

    // Autoridad
    private String autoridadReclamadaMunicipio;

    // Catastro
    private BigDecimal superficieTerreno;
    private BigDecimal superficieConstruccion;
    private String     tipoConstruccion;   // A, B, C, D
    private String     zonificacion;       // Zona III, etc.

    // Actos de aplicación
    private String     folioReciboPago;
    private BigDecimal montoPago;
    private LocalDate  fechaPrimerPago;    // base del semáforo

    // Multas históricas
    private Boolean incluyeMultasHistoricas;
    private String  aniosMultasHistoricas;
    private String  argumentacionFaltaNotificacion;

    // Transcripción de ley
    private String transcripcionLeyIngresos;

    // Documentos
    private String        rutaPdfDemandaGenerada;
    private LocalDateTime fechaGeneracionDemanda;
    private String        rutaPdfDemandaPresentada;
    private String        rutaPdfAcuseDemanda;
    private LocalDate     fechaPresentacionDemanda;

    private LocalDateTime fechaRegistro;
    private LocalDateTime ultimaActualizacion;

    private String numCuenta;
    private String clavePredial;
}