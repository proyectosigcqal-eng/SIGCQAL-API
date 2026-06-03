package com.sigcqal.api.infra.ModuloAreaSustantiva.ClasificacionJuridica.Entity;
import java.sql.Date;

import com.sigcqal.api.infra.Catalogo.Autoridad.Entity.AutoridadEntity;
import com.sigcqal.api.infra.Catalogo.EstatusDetalleExpediente.Entity.EstatusDetalleExpedienteEntity;
import com.sigcqal.api.infra.Catalogo.TipoActoEmitido.Entity.TipoActoEmitidoEntity;
import com.sigcqal.api.infra.Catalogo.TipoEntrada.Entity.TipoEntradaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "detalle_asesoria", schema = "sustantiva")
@Data

public class ClasificacionJuridicaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_asesoria")
    private Integer id;
    @Column(name = "id_expediente")
    private Integer idExpediente;
    @ManyToOne
    @JoinColumn(name = "id_tipo_acto_emitido")
    private TipoActoEmitidoEntity tipoActoEmitido;
    @ManyToOne
    @JoinColumn(name = "id_autoridad")
    private AutoridadEntity autoridad;
    @ManyToOne
    @JoinColumn(name = "id_estatus_detalle_expediente")
    private EstatusDetalleExpedienteEntity estatusDetalleExpediente;
    @ManyToOne
    @JoinColumn(name = "id_tipo_entrada")
    private TipoEntradaEntity tipoEntrada;
    @Column(name = "calificacion_acto")
    private String calificacionActo;
    @Column(name = "problematica")
    private String problematica;
    @Column(name = "seguimiento")
    private String seguimientoAsesoria;
    @Column(name = "monto")
    private Integer monto;
    @Column(name = "fecha_notificacion")
    private Date fechaNotificacion;
}
