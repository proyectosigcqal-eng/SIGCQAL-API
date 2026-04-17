package com.sigcqal.api.infra.Catalogo.Usuario.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data 
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "id_persona")
    private Long idPersona;

    @Column(name = "id_rol")
    private Long idRol;

    @Column(name = "id_area")
    private Long idArea;

    @Column(name = "usuario_login")
    private String usuarioLogin;

    @Column(name = "correo_electronico")
    private String correoElectronico;

    @Column(name = "password")
    private String Password;

    @Column(name = "fecha_registro_usuario")
    private LocalDateTime createdAt;

    @Column(name = "activo")
    private Boolean activo;
    
}
