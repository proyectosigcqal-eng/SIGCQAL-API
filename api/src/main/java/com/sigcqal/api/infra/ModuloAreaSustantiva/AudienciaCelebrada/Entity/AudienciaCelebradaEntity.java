package com.sigcqal.api.infra.ModuloAreaSustantiva.AudienciaCelebrada.Entity;

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
@Table(name = "audiencia_celebrada", schema = "sustantiva")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AudienciaCelebradaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_audiencia_celebrada")
    private Integer idAudienciaCelebrada;

    @Column(name = "id_audiencia_espera", nullable = false)
    private Integer idAudienciaEspera;

    @Column(name = "fecha_hora_celebracion")
    private LocalDateTime fechaHoraCelebracion;

    @Column(name = "numero_oficio_acta", length = 100)
    private String numeroOficioActa;

    @Column(name = "sala_o_modalidad", length = 100)
    private String salaOModalidad;

    @Column(name = "resultado_audiencia", length = 255)
    private String resultadoAudiencia;

    @Column(name = "asistio_autoridad")
    private Boolean asistioAutoridad;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;
}
