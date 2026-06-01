package com.sigcqal.api.infra.Catalogo.Expediente.Entity;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "expedientes", schema = "sustantiva")
@Data
public class ExpedienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_expedientes")
    private Long id;

    @Column(name = "folio_gobierno")
    private String folioGobierno;

    @Column(name = "fecha_solicitud")
    private String fechaSolicitud;

    @Column(name = "id_municipio")
    private Long idMunicipio;

    @Column(name = "id_asesor")
    private Long idAsesor;

    @Column(name = "id_contribuyentes")
    private Long idContribuyentes;

    @Column(name = "id_solicitante")
    private Long idSolicitante;

    @Column(name = "id_tipo_tramite")
    private Long idTipoTramite;

    @Column(name = "id_estatus_expediente")
    private Long idEstatusExpediente;

    @Column(name = "documento_acredita_personalidad")
    private String documentoAcreditaPersonalidad;

    @Column(name = "archivo_documento_acredita_personalidad")
    private String archivoDocumentoAcreditaPersonalidad;
}
