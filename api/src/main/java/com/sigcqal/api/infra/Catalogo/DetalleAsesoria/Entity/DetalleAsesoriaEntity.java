package com.sigcqal.api.infra.Catalogo.DetalleAsesoria.Entity;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "detalle_asesoria", schema = "sustantiva")
@Data
public class DetalleAsesoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_asesoria")
    private Long id;

    @Column(name = "id_expedientes")
    private Long idExpedientes;

    @Column(name = "id_tipo_acto_emitido")
    private Long idTipoActoEmitido;

    @Column(name = "id_autoridad")
    private Long idAutoridad;

    @Column(name = "calificacion_acto")
    private String calificacionActo;

    @Column(name = "id_tipo_entrada")
    private Long idTipoEntrada;

    @Column(name = "problematica")
    private String problematica;

    @Column(name = "seguimiento")
    private String seguimiento;

    @Column(name = "id_estatus_detalle_expediente")
    private Long idEstatusDetalleExpediente;

    @Column(name = "fecha_notificacion")
    private String fechaNotificacion;

    @Column(name = "monto")
    private Double monto;
}
