package com.sigcqal.api.web.ModuloAreaSustantiva.Turnado.Dto;


import lombok.Data;

@Data
public class TurnadoRequestDTO {
    private Integer idUsuario; // opcional — quién está ejecutando el turnado
    private String  motivo;    // opcional — motivo de reasignación manual
}
