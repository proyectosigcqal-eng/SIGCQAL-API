package com.sigcqal.api.domain.ModuloAreaSustantiva.BandejaRepresentacionLegal.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RepresentacionBandeja {
    private String folio;
    private String idExpediente;
    private String municipioProcedencia;
    private String contribuyente;
    private String tipoActo;
    private String estatusPrincipal;
    private String estatusSecundario;
    private String ultimaModificacionDescripcion;
    private String ultimaModificacionTimestamp;
    private Boolean bloqueado;
    private Boolean tieneFicha;
    private Boolean tieneCir;
    private Boolean tieneDemanda;
    private Boolean tieneOficio;
    private Boolean tieneAudiencia;
    private Boolean tieneSentencia;
    private Boolean tieneEjecutoria;
    private Boolean tieneCumplimiento;
    private String fechaCir;
    private String fechaDemanda;
    private String fechaOficio;
    private String fechaAudiencia;
    private String fechaSentencia;
    private String fechaEjecutoria;
    private String fechaRegistro;
    private Integer idDemandaAmparo;
    private String semaforo;
}
