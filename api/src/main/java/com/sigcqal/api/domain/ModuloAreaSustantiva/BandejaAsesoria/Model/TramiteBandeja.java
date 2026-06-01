package com.sigcqal.api.domain.ModuloAreaSustantiva.BandejaAsesoria.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TramiteBandeja {
    private String folio;
    private String municipioProcedencia;
    private String contribuyente;
    private String tipoActo;
    private String estatusPrincipal;
    private String estatusSecundario;
    private String ultimaModificacionDescripcion;
    private String ultimaModificacionTimestamp;
    private Boolean tieneBitacora;
    private Boolean tieneFicha;
}
