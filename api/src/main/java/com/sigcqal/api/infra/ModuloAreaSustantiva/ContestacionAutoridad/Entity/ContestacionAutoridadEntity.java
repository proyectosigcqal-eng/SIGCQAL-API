package com.sigcqal.api.infra.ModuloAreaSustantiva.ContestacionAutoridad.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "quejas_respuestas_autoridad", schema = "sustantiva")
@Data
public class ContestacionAutoridadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_respuesta_autoridad")
    private Long id;

    @Column(name = "id_queja")
    private Integer idQueja;

    @Column(name = "folio_expediente")
    private String folioExpediente;

    @Column(name = "numero_oficio")
    private String numeroOficio;

    @Column(name = "id_autoridad")
    private Integer idAutoridad;

    @Column(name = "nombre_titular")
    private String nombreTitular;

    @Column(name = "ruta_pdf_informe")
    private String rutaPdfInforme;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "decision")
    private String decision;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
}