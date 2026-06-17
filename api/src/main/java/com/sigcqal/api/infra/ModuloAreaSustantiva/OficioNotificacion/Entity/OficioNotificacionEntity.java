package com.sigcqal.api.infra.ModuloAreaSustantiva.OficioNotificacion.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import com.sigcqal.api.infra.Catalogo.Autoridad.Entity.AutoridadEntity;


@Entity
@Table(name = "oficio_notificacion", schema = "sustantiva")
@Data
public class OficioNotificacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_oficio_notificacion")
    private Long id;

    @Column(name = "folio_expediente", nullable = false, length = 255)
private String folioExpediente;

    @ManyToOne
    @JoinColumn(name = "id_autoridad")
    private AutoridadEntity autoridad;

    @Column(name = "num_oficio", nullable = false, length = 100)
    private String numOficio;

    @Column(name = "fecha_acuerdo", length = 100, nullable = false)
    private String fechaAcuerdo;

    @Column(columnDefinition = "TEXT")
    private String fundamento;

    @Column(name = "iniciales_asesor", length = 50)
    private String inicialesAsesor;

    @Column(name = "ruta_pdf", length = 500)
    private String rutaPdf;

    @Column(name = "fecha_generacion")
    private LocalDateTime fechaGeneracion;
}