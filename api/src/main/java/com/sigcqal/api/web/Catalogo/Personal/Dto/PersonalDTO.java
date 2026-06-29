package com.sigcqal.api.web.Catalogo.Personal.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonalDTO {
    private Long idPersonal;
    private Long idPersona;
    private String nombreCompleto; // Concatenación para facilidad en el front
    private String curp;
    private String rfc;
    private Boolean activo;
}