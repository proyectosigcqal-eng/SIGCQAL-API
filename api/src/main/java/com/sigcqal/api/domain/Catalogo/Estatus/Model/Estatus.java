package com.sigcqal.api.domain.Catalogo.Estatus.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estatus {
    private Integer idEstatus;
    private String nombreEstatus;
    private String descripcion;

}