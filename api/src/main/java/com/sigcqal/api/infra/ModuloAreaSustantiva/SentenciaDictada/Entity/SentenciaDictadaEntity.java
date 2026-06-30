package com.sigcqal.api.infra.ModuloAreaSustantiva.SentenciaDictada.Entity;

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
@Table(name = "sentencia_dictada", schema = "sustantiva")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SentenciaDictadaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sentencia")
    private Integer idSentencia;

    @Column(name = "id_audiencia_celebrada", nullable = false)
    private Integer idAudienciaCelebrada;

    @Column(name = "fecha_dictado")
    private LocalDate fechaDictado;

    @Column(name = "fecha_notificacion_sentencia")
    private LocalDate fechaNotificacionSentencia;

    @Column(name = "sentido_fallo", length = 255)
    private String sentidoFallo;

    @Column(name = "puntos_resolutivos", columnDefinition = "TEXT")
    private String puntosResolutivos;

    @Column(name = "numero_oficio_sentencia", length = 100)
    private String numeroOficioSentencia;

    @Column(name = "ruta_archivo_sentencia", length = 500)
    private String rutaArchivoSentencia;

    @Column(name = "ruta_pdf_oficio", length = 500)
    private String rutaPdfOficio;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;
}
