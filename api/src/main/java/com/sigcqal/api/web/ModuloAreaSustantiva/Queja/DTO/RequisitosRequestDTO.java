package com.sigcqal.api.web.ModuloAreaSustantiva.Queja.DTO;

import lombok.Data;

@Data
public class RequisitosRequestDTO {
    private Boolean requisitoIdentificacion;
    private Boolean requisitoActosFiscales;
    private Boolean requisitoNarrativaClara;
    private Boolean requisitoCompetenciaCedecon;
}