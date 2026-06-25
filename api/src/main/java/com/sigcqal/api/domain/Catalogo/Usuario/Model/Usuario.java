package com.sigcqal.api.domain.Catalogo.Usuario.Model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    
    private Long id;
    private Long idPersona;
    private List<Long> idRoles;
    private Long idArea;
    private String nombreArea;
    private String usuarioLogin;
    private String correoElectronico;
    private String password;
    private Boolean  activo; 

}
