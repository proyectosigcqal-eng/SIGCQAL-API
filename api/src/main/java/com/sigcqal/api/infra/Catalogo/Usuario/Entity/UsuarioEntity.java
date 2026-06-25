package com.sigcqal.api.infra.Catalogo.Usuario.Entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sigcqal.api.infra.Catalogo.Area.Entity.AreaEntity;
import com.sigcqal.api.infra.Catalogo.UsuarioRol.Entity.UsuarioRolEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "usuarios", schema = "catalogos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "usuarioRoles") 
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "id_persona")
    private Long idPersona;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "id_area")
    private AreaEntity idArea;

    @Column(name = "usuario_login")
    private String usuarioLogin;

    @Column(name = "correo_electronico")
    private String correoElectronico;

    @Column(name = "password")
    private String password;

    @Column(name = "fecha_registro_usuario")
    private LocalDateTime createdAt;

    @Column(name = "activo")
    private Boolean activo;
    
    @JsonIgnore
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, 
               cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UsuarioRolEntity> usuarioRoles = new HashSet<>();
    
}
