package com.sigcqal.api.web.Catalogo.Personal.Dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class PersonalRequestDTO implements Serializable {
    // Datos de la Persona (los necesarios para el registro)
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
    private String tipoIdentificacion;
    private Boolean activo;

    // Datos de Dirección (Anidados)
    private String calle;
    private String numExt;
    private String colonia;
    private String cp;
    private Integer idMunicipio;
    private Integer idEstado;
}