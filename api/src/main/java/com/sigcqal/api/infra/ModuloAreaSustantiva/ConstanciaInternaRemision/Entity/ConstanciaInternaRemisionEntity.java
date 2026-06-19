package com.sigcqal.api.infra.ModuloAreaSustantiva.ConstanciaInternaRemision.Entity;

import java.time.LocalDateTime;

import com.sigcqal.api.infra.Catalogo.Asesor.Entity.AsesorEntity;
import com.sigcqal.api.infra.Catalogo.Area.Entity.AreaEntity;
import com.sigcqal.api.infra.Catalogo.TipoTramite.Entity.TipoTramiteEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Entity.QuejaEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "quejas_cir", schema = "sustantiva")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConstanciaInternaRemisionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cir")
    private Long idCir;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_queja", nullable = true)
    private QuejaEntity queja;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_expediente")
    private ExpedienteEntity expediente;

    @Column(name = "fecha_emision")
    private LocalDateTime fechaEmision;

    @Column(name = "ruta_pdf_cir", length = 500)
    private String rutaPdfCir;

    @Column(name = "documentacion_remite", columnDefinition = "TEXT")
    private String documentacionRemite;

    @Column(name = "motivos_remite", columnDefinition = "TEXT")
    private String motivosRemite;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "asesor_que_remite", length = 255)
    private String asesorQueRemite;

    @Column(name = "fundamentos", columnDefinition = "TEXT")
    private String fundamentos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_asesor_ejecutor")
    private AsesorEntity asesorEjecutor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area_recibe")
    private AreaEntity areaRecibe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area_remite")
    private AreaEntity areaRemite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_tramite")
    private TipoTramiteEntity tipoTramite;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }
}