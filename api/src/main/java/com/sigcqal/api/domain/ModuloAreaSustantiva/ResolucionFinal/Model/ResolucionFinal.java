package com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResolucionFinal {
    private Integer id;
    private Integer idExpediente;
    private TipoResolucion tipoResolucion;
    private String rutaDocumento;
    private Date fechaEmision;
    private Integer idEstatus;
    private String nombreEstatus;

}
