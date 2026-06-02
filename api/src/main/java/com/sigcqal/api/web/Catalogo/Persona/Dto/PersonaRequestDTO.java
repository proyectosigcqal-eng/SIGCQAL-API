package com.sigcqal.api.web.Catalogo.Persona.Dto;

import lombok.Data;

@Data
public class PersonaRequestDTO {
    private Integer idDireccion;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String curp;
    private String telefono;
    private String comunidad;
    private String rfc;
    private String rec;
    private String identificacionOficial;
    private String telefonoFijo;
    private String numeroIdFolio;
    private String correo;
    private Integer idTipoPersona;
}