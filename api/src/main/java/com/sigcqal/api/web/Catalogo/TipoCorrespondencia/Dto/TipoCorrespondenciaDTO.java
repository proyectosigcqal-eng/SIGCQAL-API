package com.sigcqal.api.web.Catalogo.TipoCorrespondencia.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoCorrespondenciaDTO {
    private Integer idTipo;
    private String idNatural;
    private String descripcion;
}
