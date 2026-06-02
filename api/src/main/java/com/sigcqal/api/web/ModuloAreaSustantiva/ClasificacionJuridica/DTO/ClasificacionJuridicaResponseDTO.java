package com.sigcqal.api.web.ModuloAreaSustantiva.ClasificacionJuridica.DTO;


import java.sql.Date;

import lombok.Data;

@Data
public class ClasificacionJuridicaResponseDTO {
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
