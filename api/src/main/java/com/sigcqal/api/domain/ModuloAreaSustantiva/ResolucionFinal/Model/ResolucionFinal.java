package com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Model;
 
import java.time.LocalDate;
import java.time.LocalDateTime;
 
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

 
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResolucionFinal {
 
    private Integer       idResolucionFinal;
    private LocalDate     fechaEmisionResolucion;
    private String        conceptoCobro;
    private String        contactoVia;
    private Integer       numeroCredito;
    private String        folioCredito;
    private Integer       idExpediente;
    private Integer       idAri;
    private Integer       idQuejaRespuestaAutoridad;
    private Integer       idEstatusQueja;
    private String        rutaResolucionFinal;
    private LocalDateTime fechaEmision;
    private Integer       idEstatusExpediente;

    private String numeroOficio;
    private String folioGobierno;
    private String nombreContribuyente;
    private String identificacionOficial;
    private String numExpedienteOficial;
    private String numeroCreditoAri;
}
 
