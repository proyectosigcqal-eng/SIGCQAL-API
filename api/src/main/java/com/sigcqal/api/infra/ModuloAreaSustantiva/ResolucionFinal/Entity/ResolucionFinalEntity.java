package com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Entity;

import java.sql.Date;

import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Model.TipoResolucion;
import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Entity.EstatusExpedienteEntity;

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
@Table(name = "resolucion_final", schema = "sustantiva")
@Data
public class ResolucionFinalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resolucion_final")
    private Integer id;

    @Column(name = "id_expediente", nullable = false)
    private Integer idExpediente;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_resolucion", nullable = false)
    private TipoResolucion tipoResolucion;

    @Column(name = "ruta_documento", nullable = false)
    private String rutaDocumento;

    @Column(name = "fecha_emision", nullable = false)
    private Date fechaEmision;

    @ManyToOne
    @JoinColumn(name = "id_estatus_expediente")
    private EstatusExpedienteEntity estatusExpediente;
}
