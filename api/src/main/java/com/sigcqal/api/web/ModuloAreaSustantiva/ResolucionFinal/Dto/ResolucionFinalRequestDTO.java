package com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Dto;

import java.sql.Date;

import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Model.TipoResolucion;

import lombok.Data;

@Data
public class ResolucionFinalRequestDTO {
    private Integer idExpediente;
    private TipoResolucion tipoResolucion;
    private String rutaDocumento;
    private Date fechaEmision;
}
