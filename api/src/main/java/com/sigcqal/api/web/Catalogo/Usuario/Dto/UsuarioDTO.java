package com.sigcqal.api.web.Catalogo.Usuario.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private Long idPersona;
    private Long idRol;
    private Long idArea;
    private String nombreArea;
    private String usuarioLogin;
    private String correoElectronico;
}
