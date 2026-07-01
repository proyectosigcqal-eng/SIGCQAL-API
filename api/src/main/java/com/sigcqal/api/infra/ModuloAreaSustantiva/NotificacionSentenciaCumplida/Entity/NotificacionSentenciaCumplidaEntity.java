package com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionSentenciaCumplida.Entity;

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
@Table(name = "notificacion_sentencia_cumplida", schema = "sustantiva")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionSentenciaCumplidaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sentencia_cumplida")
    private Integer idSentenciaCumplida;

    @Column(name = "id_sentencia_ejecutoria", nullable = false)
    private Integer idSentenciaEjecutoria;

    @Column(name = "numero_oficio_cumplimiento", length = 100)
    private String numeroOficioCumplimiento;

    @Column(name = "numero_oficio_archivo", length = 100)
    private String numeroOficioArchivo;

    @Column(name = "fecha_notificacion_archivo")
    private LocalDate fechaNotificacionArchivo;

    @Column(name = "observaciones_finales", length = 500)
    private String observacionesFinales;

    @Column(name = "ruta_pdf_oficio", length = 500)
    private String rutaPdfOficio;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;
}
