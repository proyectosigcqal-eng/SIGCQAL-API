package com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List; // ← AGREGAR

import com.sigcqal.api.infra.Catalogo.Asesor.Entity.AsesorEntity;
import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Entity.EstatusExpedienteEntity;
import com.sigcqal.api.infra.Catalogo.Municipio.Entity.MunicipioEntity;
import com.sigcqal.api.infra.Catalogo.Persona.Entity.PersonaEntity;
import com.sigcqal.api.infra.Catalogo.TipoTramite.Entity.TipoTramiteEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Entity.QuejaEntity; // ← AGREGAR

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "expedientes", schema = "sustantiva")
@Data
public class ExpedienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_expediente")
    private Integer id;

    @Column(name = "folio_gobierno")
    private String folioGobierno;

    @Column(name = "fecha_solicitud")
    private LocalDateTime fechaSolicitud;

    @ManyToOne
    @JoinColumn(name = "id_municipio")
    private MunicipioEntity municipio;

    @ManyToOne
    @JoinColumn(name = "id_asesor")
    private AsesorEntity asesor;

    @ManyToOne
    @JoinColumn(name = "id_contribuyente")
    private ContribuyenteEntity contribuyente;

    @ManyToOne
    @JoinColumn(name = "id_solicitante")
    private PersonaEntity solicitante;

    @ManyToOne
    @JoinColumn(name = "id_representante_legal")
    private PersonaEntity representanteLegal;

    @ManyToOne
    @JoinColumn(name = "id_tipo_tramite")
    private TipoTramiteEntity tipoTramite;

    @ManyToOne
    @JoinColumn(name = "id_estatus_expediente")
    private EstatusExpedienteEntity estatusExpediente;

    // ✅ AGREGAR ESTA RELACIÓN:
    @OneToMany(mappedBy = "expediente", fetch = FetchType.LAZY)
    private List<QuejaEntity> quejas;

    @Column(name = "documento_acredita_personalidad")
    private String documentoAcreditaPersonalidad;

    @Column(name = "archivo_documento_acredita_personalidad")
    private String archivoDocumentoAcreditaPersonalidad;

    @Column(name = "estado_alerta_5_dias")
    private String estadoAlerta5Dias;

    @Column(name = "fecha_envio_oficio_autoridad")
    private LocalDateTime fechaEnvioOficioAutoridad;

    @Column(name = "fecha_limite_informe")
    private LocalDate fechaLimiteInforme;

    @Column(name = "fecha_recepcion_informe")
    private LocalDateTime fechaRecepcionInforme;

    @Column(name = "fojas_informe")
    private Integer fojasInforme;

    @Column(name = "notificacion_vencimiento_enviada")
    private Boolean notificacionVencimientoEnviada;

    @Column(name = "numero_oficio_respuesta")
    private String numeroOficioRespuesta;

    @Column(name = "ruta_pdf_informe")
    private String rutaPdfInforme;

    @Column(name = "bloqueado")
    private Boolean bloqueado = false;

    @Column(name = "fecha_cierre_automatico")
    private java.time.LocalDateTime fechaCierreAutomatico;
}