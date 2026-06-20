package com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAcci.Entity;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "quejas_acci", schema = "sustantiva")
@Data
public class QuejasAcciEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_acci")
    private Long id;

    @Column(name = "id_queja", nullable = false)
    private Long idQueja;

    @Column(name = "id_oficio_autoridad")
    private Long idOficioAutoridad;

    @Column(name = "justificacion_investigacion", columnDefinition = "TEXT")
    private String justificacionInvestigacion;

    @Column(name = "nuevos_requerimientos_autoridad", columnDefinition = "TEXT")
    private String nuevosRequerimientosAutoridad;

    @Column(name = "plazo_dias_habiles")
    private Integer plazoDiasHabiles;

    @Column(name = "fecha_emision_acci")
    private LocalDateTime fechaEmisionAcci;

    @Column(name = "ruta_pdf_acci", length = 500)
    private String rutaPdfAcci;

    @Column(name = "concluido")
    private Boolean concluido = false;

    @Column(name = "fecha_conclusion")
    private LocalDateTime fechaConclusion;
}