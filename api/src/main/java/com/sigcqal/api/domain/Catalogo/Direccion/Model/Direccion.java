package com.sigcqal.api.domain.Catalogo.Direccion.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Direccion {
    private Long id;
    private String calle;
    private String numExt;
    private String numInt;
    private String colonia;
    private String cp;
    private Integer idMunicipio;
    private Integer idEstado;

    
}
