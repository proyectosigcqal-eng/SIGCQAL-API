package com.sigcqal.api.infra.ModuloAreaSustantiva.Persistence.Entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;

import com.sigcqal.api.infra.Catalogo.Persona.Entity.PersonaEntity;
import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Entity.EstatusExpedienteEntity;
import com.sigcqal.api.infra.Catalogo.Municipio.Entity.MunicipioEntity;
import com.sigcqal.api.infra.Catalogo.TipoTramite.Entity.TipoTramiteEntity;

@Entity
@Table(name = "expedientes", schema = "sustantiva")
@Data
public class ExpedienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_expediente", nullable = false)
    private Long idExpediente;

    @Column(name = "folio_gobierno", nullable = false, length = 30)
    private String folioGobierno;

    @Column(name = "fecha_solicitud")
    private LocalDateTime fechaSolicitud;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_municipio", nullable = false)
    private MunicipioEntity municipio; // Si no tienes la Entity de Municipio, usa el ID directamente

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_asesor", nullable = false)
    private AsesorEntity asesor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_contribuyente", nullable = false)
    private ContribuyenteEntity contribuyente; // Corregido: apunta a Contribuyente

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_solicitante", nullable = false)
    private PersonaEntity solicitante; // Corregido: apunta a Persona

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_tramite", nullable = false)
    private TipoTramiteEntity tipoTramite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estatus_expediente", nullable = false)
    private EstatusExpedienteEntity estatusExpediente;

    @Column(name = "documento_acredita_personalidad", length = 255)
    private String documentoAcreditaPersonalidad;

    @Column(name = "archivo_documento_acredita_personalidad", length = 255)
    private String archivoDocumentoAcreditaPersonalidad;
}