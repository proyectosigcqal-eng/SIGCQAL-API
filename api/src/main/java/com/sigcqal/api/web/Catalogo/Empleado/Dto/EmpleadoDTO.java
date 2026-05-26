package com.sigcqal.api.web.Catalogo.Empleado.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoDTO {
    private Long id;
    private String nombreCompleto;
    private String cargo;
    private Integer idArea;
}
