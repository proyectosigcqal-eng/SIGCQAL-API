package com.sigcqal.api.web.Admin.Dto;

import lombok.Data;

@Data
public class AsesorAdminRequestDTO {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String especialidad;
    private String rfc;
    private String correo;
    private String telefono;
}