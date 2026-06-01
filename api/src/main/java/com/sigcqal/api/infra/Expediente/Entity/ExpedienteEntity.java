package com.sigcqal.api.infra.Expediente.Entity;

import java.time.LocalDateTime;

import com.sigcqal.api.infra.Catalogo.Asesor.Entity.AsesorEntity;
import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Entity.EstatusExpedienteEntity;
import com.sigcqal.api.infra.Catalogo.Municipio.Entity.MunicipioEntity;
import com.sigcqal.api.infra.Catalogo.Persona.Entity.PersonaEntity;
import com.sigcqal.api.infra.Catalogo.TipoTramite.Entity.TipoTramiteEntity;

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
    @JoinColumn(name = "id_tipo_tramite")
    private TipoTramiteEntity tipoTramite;

    @ManyToOne
    @JoinColumn(name = "id_estatus_expediente")
    private EstatusExpedienteEntity estatusExpediente;

    @Column(name = "documento_acredita_personalidad")
    private String documentoAcreditaPersonalidad;

    @Column(name = "archivo_documento_acredita_personalidad")
    private String archivoDocumentoAcreditaPersonalidad;
}
