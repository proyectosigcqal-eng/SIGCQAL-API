package com.sigcqal.api.infra.ModuloAreaSustantiva.AudienciaEspera.Entity;

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
@Table(name = "audiencia_espera", schema = "sustantiva")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AudienciaEsperaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_audiencia_espera")
    private Integer idAudienciaEspera;

    @Column(name = "id_demanda_amparo", nullable = false)
    private Integer idDemandaAmparo;

    @Column(name = "numero_oficio_admision", length = 100)
    private String numeroOficioAdmision;

    @Column(name = "fecha_notificacion_oficio")
    private LocalDate fechaNotificacionOficio;

    @Column(name = "fecha_hora_audiencia_prog")
    private LocalDateTime fechaHoraAudienciaProg;

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    @Column(name = "ruta_pdf_oficio", length = 500)
    private String rutaPdfOficio;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;
}
