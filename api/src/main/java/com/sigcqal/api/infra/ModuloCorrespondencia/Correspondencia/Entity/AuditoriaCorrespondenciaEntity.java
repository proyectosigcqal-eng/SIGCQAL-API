package com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Entity;

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
@Table(name = "auditoria_correspondencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditoriaCorrespondenciaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_auditoria_correspondencia")
    private Long id;

    @Column(name = "id_correspondencia")
    private Long idCorrespondencia;

    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "accion")
    private String accion;

    @Column(name = "folio_unico")
    private String folioUnico;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
}
