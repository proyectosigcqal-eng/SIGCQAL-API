package com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resolucion_final", schema = "sustantiva")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResolucionFinalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resolucion_final")
    private Integer idResolucionFinal;

    @Column(name = "fecha_emision_resolucion", nullable = false)
    private LocalDate fechaEmisionResolucion;

    @Column(name = "concepto_cobro", length = 255)
    private String conceptoCobro;

    @Column(name = "contacto_via", length = 100)
    private String contactoVia;

    @Column(name = "numero_credito")
    private Integer numeroCredito;

    @Column(name = "folio_credito", length = 50)
    private String folioCredito;

    // -------------------------------------------------------------------
    // Llaves foráneas guardadas como columna simple (sin @ManyToOne) para
    // mantener el mismo patrón liviano usado en otras entidades del módulo.
    // Si más adelante se requiere navegar la relación completa (ej. traer
    // el expediente completo), se puede añadir el @ManyToOne correspondiente.
    // -------------------------------------------------------------------
    @Column(name = "id_expediente", nullable = false)
    private Integer idExpediente;

    @Column(name = "id_ari", nullable = false)
    private Integer idAri;

    @Column(name = "id_queja_respuesta_autoridad", nullable = false)
    private Integer idQuejaRespuestaAutoridad;

    @Column(name = "id_estatus_queja", nullable = false)
    private Integer idEstatusQueja;
}