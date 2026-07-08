package com.sigcqal.api.web.Catalogo.Usuario.Dto;

import lombok.Data;

@Data
public class ActualizarUsuarioRequestDTO {
        private String  nombre;
    private String  apellidoPaterno;
    private String  apellidoMaterno;
    private String  correo;
    private String  password;
    private Integer idArea;
}