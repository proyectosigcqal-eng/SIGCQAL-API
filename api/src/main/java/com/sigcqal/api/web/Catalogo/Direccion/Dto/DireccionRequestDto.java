package com.sigcqal.api.web.Catalogo.Direccion.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionRequestDto {
    private String calle;
    private String numExt;
    private String numInt;
    private String colonia;
    private String cp;
    private Integer idMunicipio;
    private Integer idEstado;
}