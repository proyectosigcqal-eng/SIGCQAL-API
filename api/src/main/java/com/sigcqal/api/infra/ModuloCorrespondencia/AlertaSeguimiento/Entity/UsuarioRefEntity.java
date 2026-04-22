package com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Entity;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Immutable
@Data
public class UsuarioRefEntity {
    @Id
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "id_persona")
    private Long idPersona;

    @Column(name = "id_area")
    private Long idArea;

    @Column(name = "usuario_login")
    private String usuarioLogin;
}

