package com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoOficio.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.sigcqal.api.infra.Catalogo.Estatus.Entity.EstatusEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.Oficio.Entity.OficioEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seguimiento_oficio", schema = "correspondencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeguimientoOficioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguimiento_oficio")
    private Integer idSeguimientoOficio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_oficio")
    private OficioEntity oficio;

    @Column(name = "folio_respuesta", insertable = false, updatable = false)
    private Integer folioRespuesta;

    @Column(name = "respuesta_seguimiento_oficio", columnDefinition = "TEXT")
    private String respuestas;

    @Column(name = "fecha_resolucion")
    private LocalDate fechaResolucion;

    @Column(name = "hora_resolucion")
    private LocalTime horaResolucion;

    @Column(name = "archivo_adjunto", length = 255)
    private String archivoAdjunto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estatus")
    private EstatusEntity estatus;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void onCreate() {
        if (this.fechaRegistro == null) {
            this.fechaRegistro = LocalDateTime.now();
        }
    }
}