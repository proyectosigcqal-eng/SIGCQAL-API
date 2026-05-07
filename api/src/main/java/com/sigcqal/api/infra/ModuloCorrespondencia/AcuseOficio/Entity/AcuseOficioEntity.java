package com.sigcqal.api.infra.ModuloCorrespondencia.AcuseOficio.Entity;

import java.time.LocalDate;
import java.time.LocalTime;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.Oficio.Entity.OficioEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "acuse_oficio" , schema = "correspondencia") 
@Data
public class AcuseOficioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_acuse_oficio")
    private Long idAcuseOficio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_oficio")
    private OficioEntity oficio;

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