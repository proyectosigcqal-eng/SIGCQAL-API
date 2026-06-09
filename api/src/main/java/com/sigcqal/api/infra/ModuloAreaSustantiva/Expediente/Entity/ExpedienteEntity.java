package com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.sigcqal.api.infra.Catalogo.Asesor.Entity.AsesorEntity;
import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Entity.EstatusExpedienteEntity;
import com.sigcqal.api.infra.Catalogo.Municipio.Entity.MunicipioEntity;
import com.sigcqal.api.infra.Catalogo.Persona.Entity.PersonaEntity;
import com.sigcqal.api.infra.Catalogo.TipoTramite.Entity.TipoTramiteEntity;
import com.sigcqal.api.domain.ModuloAreaSustantiva.InformeAutoridad.Model.EstadoAlertaPlazo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    @Column(name = "documento_acredita_personalidad")
    private String documentoAcreditaPersonalidad;

    @Column(name = "archivo_documento_acredita_personalidad")
    private String archivoDocumentoAcreditaPersonalidad;

    @Column(name = "fecha_envio_oficio_autoridad")
    private LocalDateTime fechaEnvioOficio;

    @Column(name = "fecha_limite_informe")
    private LocalDate fechaLimiteInforme;

    @Column(name = "numero_oficio_respuesta", length = 50)
    private String numeroOficioRespuesta;

    @Column(name = "fojas_informe")
    private Integer fojasInforme;

    @Column(name = "fecha_recepcion_informe")
    private LocalDateTime fechaRecepcionInforme;

    @Column(name = "ruta_pdf_informe", length = 500)
    private String rutaPdfInforme;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_alerta_5dias", length = 30)
    private EstadoAlertaPlazo estadoAlertaPlazo;

    @Column(name = "notificacion_vencimiento_enviada")
    private Boolean notificacionVencimientoEnviada;
}
