package com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAcci.Entity;


import java.time.LocalDateTime;

import com.sigcqal.api.infra.ModuloAreaSustantiva.OficioNotificacion.Entity.OficioNotificacionEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Entity.QuejaEntity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "quejas_acci", schema = "sustantiva")
@Data

@NoArgsConstructor
@AllArgsConstructor
@Builder

public class QuejasAcciEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_acci")
    private Integer idAcci;

    // Relación con Queja
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_queja", nullable = false)
    private QuejaEntity queja;

    // Relación con Oficio Autoridad
    @ManyToOne
        @JoinColumn(name = "id_oficio_autoridad")
        private OficioNotificacionEntity oficioNotificacion; 

    @Column(name = "justificacion_investigacion", nullable = false, columnDefinition = "TEXT")
    private String justificacionInvestigacion;

    @Column(name = "nuevos_requerimientos_autoridad", nullable = false, columnDefinition = "TEXT")

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