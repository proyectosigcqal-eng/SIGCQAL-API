package com.sigcqal.api.infra.ModuloCorrespondencia.AcuseCorrespondencia.Entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Entity.CorrespondenciaEntity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "acuse_correspondencia")
@Data
public class AcuseCorrespondenciaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_acuse_correspondencia")
    private Long idAcuseCorrespondencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_correspondencia")
    private CorrespondenciaEntity correspondencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_revisor")
    private UsuarioEntity usuarioRevisor;

    @Column(name = "fecha_aceptacion")
    private LocalDate fechaAceptacion;

    @Column(name = "hora_aceptacion")
    private LocalTime horaAceptacion;

    @Column(name = "es_del_area")
    private Boolean esDelArea;
}