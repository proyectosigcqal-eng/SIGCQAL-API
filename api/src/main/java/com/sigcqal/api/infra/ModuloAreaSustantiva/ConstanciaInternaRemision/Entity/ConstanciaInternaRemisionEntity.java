package com.sigcqal.api.infra.ModuloAreaSustantiva.ConstanciaInternaRemision.Entity;

import java.time.LocalDateTime;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Entity.QuejaEntity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "quejas_cir", schema = "sustantiva")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConstanciaInternaRemisionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación correcta con la tabla quejas (ajusta según tu diagrama)
    @ManyToOne
    @JoinColumn(name = "id_queja", nullable = false) 
    private QuejaEntity queja;

    @ManyToOne
    @JoinColumn(name = "expediente_id", nullable = false)
    private ExpedienteEntity expediente;

    @Column(name = "ruta_pdf")
    private String rutaPdf;

    @Column(name = "analisis_juridico")
    private String analisisJuridico;

    @Column(name = "determinacion")
    private String determinacion;

    @Column(name = "informe_autoridad_fecha")
    private String informeAutoridadFecha;

    @Column(name = "informe_autoridad_texto", columnDefinition = "TEXT")
    private String informeAutoridadTexto;

    @Column(name = "informe_autoridad_asunto")
    private String informeAutoridadAsunto;

    @Column(name = "autoridad_contesto")
    private Boolean autoridadContesto;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
}
