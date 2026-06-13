package com.sigcqal.api.web.ModuloAreaSustantiva.ClasificacionJuridica.DTO;

import java.sql.Date;

import lombok.Data;

@Data
public class ClasificacionJuridicaRequestDTO {
    private Integer idExpediente;
    private String folioGobierno; 
    private Integer tipoActo;
    private Integer idAutoridad;
    private Integer idEstatusDetalleExpediente;
    private Integer idTipoEntrada;
    private String calificacionActo;
    private String problematica;
    private String seguimientoAsesoria;
    private Integer monto;
    private Date fechaNotificacion;
    private String nombreAutoridad;
    private String nombreTipoActo;
    private String nombreEstatusDetalle;
    private String nombreTipoEntrada;
}
