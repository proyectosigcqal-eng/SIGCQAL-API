package com.sigcqal.api.infra.ModuloCorrespondencia.AcuseReciboInterno.Entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.Memorandum.Entity.MemorandumEntity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "acuserecibointerno" , schema = "correspondencia")
@Data
public class AcuseReciboInternoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_acuse")
    private Long idAcuse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_memorandum")
    private MemorandumEntity memorandum;

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