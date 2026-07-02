package com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAri.Entity.QuejasAriEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ContestacionAutoridad.Entity.ContestacionAutoridadEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    // ✅ Se agrega insertable/updatable = false para evitar duplicidad con el @ManyToOne
    @Column(name = "id_expediente", nullable = false, insertable = false, updatable = false)
    private Integer idExpediente;

    // ✅ Se agrega insertable/updatable = false
    @Column(name = "id_ari", nullable = false, insertable = false, updatable = false)
    private Integer idAri;

    // ✅ Cambiado a Long (para que coincida con ContestacionAutoridadEntity) e insertable/updatable = false
    @Column(name = "id_queja_respuesta_autoridad", nullable = true, insertable = false, updatable = false)
    private Long idQuejaRespuestaAutoridad;

    @Column(name = "id_estatus_queja", nullable = false)
    private Integer idEstatusQueja;

    @Column(name = "ruta_resolucion_final", length = 500)
    private String rutaResolucionFinal;

    @Column(name = "fecha_emision")
    private LocalDateTime fechaEmision;

    @Column(name = "id_estatus_expediente", nullable = false)
    private Integer idEstatusExpediente;

    // =======================================================================
    // RELACIONES MAPEADAS (Estas controlan la escritura en la BD)
    // =======================================================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_expediente", referencedColumnName = "id_expediente")
    private ExpedienteEntity expediente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_queja_respuesta_autoridad", referencedColumnName = "id_respuesta_autoridad", nullable = true)
    private ContestacionAutoridadEntity contestacionAutoridad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ari", referencedColumnName = "id_ari", nullable = true)
    private QuejasAriEntity quejaAri;
}