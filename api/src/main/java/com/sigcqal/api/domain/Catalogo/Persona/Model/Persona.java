package com.sigcqal.api.domain.Catalogo.Persona.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Persona {
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
}
