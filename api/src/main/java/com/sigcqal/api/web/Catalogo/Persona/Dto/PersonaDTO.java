package com.sigcqal.api.web.Catalogo.Persona.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDTO {
    private Long id;
    private Long idDireccion;
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
    private Long idTipoPersona;
    private String nomreTipoPersona; 
    private String tipoIdentificacion;
}
