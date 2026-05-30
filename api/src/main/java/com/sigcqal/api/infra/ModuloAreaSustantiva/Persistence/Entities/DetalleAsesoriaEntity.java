package com.sigcqal.api.infra.ModuloAreaSustantiva.Persistence.Entities;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.sigcqal.api.infra.Catalogo.Autoridad.Entity.AutoridadEntity;
import com.sigcqal.api.infra.Catalogo.EstatusDetalleExpediente.Entity.EstatusDetalleExpedienteEntity;
import com.sigcqal.api.infra.Catalogo.TipoActoEmitido.Entity.TipoActoEmitidoEntity;
import com.sigcqal.api.infra.Catalogo.TipoEntrada.Entity.TipoEntradaEntity;

@Entity
@Table(name = "detalle_asesoria", schema = "sustantiva") // Ajusta el esquema si es necesario
@Data
public class DetalleAsesoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_asesoria", nullable = false)
    private Long idDetalleAsesoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_expediente", nullable = false)
    private ExpedienteEntity expediente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_acto_emitido", nullable = false)
    private TipoActoEmitidoEntity tipoActoEmitido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_autoridad", nullable = false)
    private AutoridadEntity autoridad;

    @Column(name = "calificacion_acto", columnDefinition = "TEXT")
    private String calificacionActo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_entrada", nullable = false)
    private TipoEntradaEntity tipoEntrada;

    @Column(name = "problematica", columnDefinition = "TEXT")
    private String problematica;

    @Column(name = "seguimiento", columnDefinition = "TEXT")
    private String seguimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estatus_detalle_expediente", nullable = false)
    private EstatusDetalleExpedienteEntity estatusDetalleExpediente;

    @Column(name = "fecha_notificacion")
    private LocalDate fechaNotificacion;

    @Column(name = "monto", precision = 15, scale = 2)
    private BigDecimal monto;

}