package com.sigcqal.api.infra.ModuloAreaSustantiva.SentenciaEjecutoria.Entity;

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
@Table(name = "sentencia_ejecutoria", schema = "sustantiva")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SentenciaEjecutoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sentencia_ejecutoria")
    private Integer idSentenciaEjecutoria;

    @Column(name = "id_sentencia", nullable = false)
    private Integer idSentencia;

    @Column(name = "id_recurso_revision")
    private Integer idRecursoRevision;

    @Column(name = "numero_oficio_ejecutoria", length = 100)
    private String numeroOficioEjecutoria;

    @Column(name = "fecha_declaracion_ejecutoria")
    private LocalDate fechaDeclaracionEjecutoria;

    @Column(name = "requerimiento_cumplimiento", length = 500)
    private String requerimientoCumplimiento;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;
}
