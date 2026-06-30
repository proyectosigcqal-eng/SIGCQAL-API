package com.sigcqal.api.infra.ModuloAreaSustantiva.RecursoRevision.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Table(name = "recurso_revision", schema = "sustantiva")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecursoRevisionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recurso_revision")
    private Integer idRecursoRevision;

    @Column(name = "id_sentencia", nullable = false)
    private Integer idSentencia;

    @Column(name = "numero_oficio_interposicion", length = 100)
    private String numeroOficioInterposicion;

    @Column(name = "numero_expediente_revision", length = 100)
    private String numeroExpedienteRevision;

    @Column(name = "tribunal_colegiado_asig", length = 255)
    private String tribunalColegiadoAsig;

    @Column(name = "fecha_interposicion")
    private LocalDate fechaInterposicion;

    @Column(name = "observaciones_seguimiento", length = 500)
    private String observacionesSeguimiento;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;
}
