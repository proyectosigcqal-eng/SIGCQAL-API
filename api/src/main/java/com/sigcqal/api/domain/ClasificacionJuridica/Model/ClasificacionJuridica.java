package com.sigcqal.api.domain.ClasificacionJuridica.Model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ClasificacionJuridica {
    private Integer id;
    private Integer idExpediente;
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
