package com.sigcqal.api.web.Catalogo.Area.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaDTO {
    private Long id;
    private String nombre;
    private String descripcion;
}
