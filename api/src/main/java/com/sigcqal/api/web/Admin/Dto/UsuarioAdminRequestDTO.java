package com.sigcqal.api.web.Admin.Dto;

import lombok.Data;

@Data
public class UsuarioAdminRequestDTO {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String usuarioLogin;
    private String password;
    private String correo;
    private Long idArea;
}