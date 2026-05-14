package com.sigcqal.api.domain.Catalogo.TipoCorrespondencia.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoCorrespondencia {
    private Integer idTipo;
    private String descripcion;
    private Boolean activo;
}
