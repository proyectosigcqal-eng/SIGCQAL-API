package com.sigcqal.api.infra.ModuloAreaSustantiva.RepresentacionLegal.Entity;

import java.time.LocalDateTime;

import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Entity.QuejaEntity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "representacion_legal", schema = "sustantiva")
@Data
public class RepresentacionLegalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /** Para IRL Directo — NULL para Evolución */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_expediente")
    private ExpedienteEntity expediente;

    /** Para IRL Evolución — NULL para Directo */
    @Column(name = "id_resolucion_final")
    private Integer idResolucionFinal;

    /** Para IRL Evolución — NULL para Directo */
    @Column(name = "id_queja_origen")
    private Integer idQuejaOrigen;

    @Column(name = "es_evolucion", nullable = false)
    private Boolean esEvolucion = false;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
}