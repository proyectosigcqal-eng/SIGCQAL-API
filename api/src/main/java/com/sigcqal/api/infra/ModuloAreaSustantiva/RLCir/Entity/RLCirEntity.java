package com.sigcqal.api.infra.ModuloAreaSustantiva.RLCir.Entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rl_cir", schema = "sustantiva")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RLCirEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rl_cir")
    private Long idRlCir;

    @Column(name = "id_expediente")
    private Long idExpediente;

    @Column(name = "fecha_emision")
    private LocalDateTime fechaEmision;

    @Column(name = "motivos", columnDefinition = "TEXT")
    private String motivos;

    @Column(name = "articulos", columnDefinition = "TEXT")
    private String articulos;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "id_asesor_remitente")
    private Long idAsesorRemitente;

    @Column(name = "id_asesor_recibe")
    private Long idAsesorRecibe;

    @Column(name = "director")
    private String director;

    @Column(name = "ruta_pdf_rl_cir")
    private String rutaPdfRlCir;

    @PrePersist
    protected void onCreate() {
        if (this.fechaEmision == null) {
            this.fechaEmision = LocalDateTime.now();
        }
    }
}