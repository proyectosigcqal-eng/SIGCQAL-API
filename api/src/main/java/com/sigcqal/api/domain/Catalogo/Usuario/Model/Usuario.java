package com.sigcqal.api.domain.Catalogo.Usuario.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    
    private Long id;
    private Long idPersona;
    private Long idRol;
    private Long idArea;
    private String usuarioLogin;
    private String correoElectronico;
    private String Password;

}
