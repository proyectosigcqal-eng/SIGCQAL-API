package com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Dto;

import java.sql.Date;

import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Model.TipoResolucion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResolucionFinalResponseDTO {
    private TipoResolucion tipoResolucion;
    private String rutaDocumento;
    private Date fechaEmision;
    private String nombreEstatus;
}
