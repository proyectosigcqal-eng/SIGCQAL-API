package com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAri.Entity;

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
@Table(name = "quejas_ari", schema = "sustantiva")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuejasAriEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ari")
    private Long idAri;

    @Column(name = "id_queja")
    private Long idQueja;

    @Column(name = "id_cir")
    private Long idCir;

    @Column(name = "num_expediente_oficial")
    private String numExpedienteOficial;

    @Column(name = "sintesis_actos_omisiones", columnDefinition = "TEXT")
    private String sintesisActosOmisiones;

    @Column(name = "nombre_encargado_firma")
    private String nombreEncargadoFirma;

    @Column(name = "fecha_acuerdo")
    private LocalDateTime fechaAcuerdo;

    @Column(name = "ruta_pdf_ari")
    private String rutaPdfAri;

    @Column(name = "id_plantilla_queja_ari")
    private Long idPlantillaQuejaAri;

    @Column(name = "multas_requerimientos", columnDefinition = "TEXT")
    private String multasRequerimientos;

    @Column(name = "multas_credito", columnDefinition = "TEXT")
    private String multasCredito;

    @Column(name = "instituto", columnDefinition = "TEXT")
    private String instituto;

    @Column(name = "siglas_abreviatura_encargado", columnDefinition = "TEXT")
    private String abreviaturaEncargado;
    // ELIMINADO: multas_creditos (indicaste que no va en esta tabla)

    @PrePersist
    protected void onCreate() {
        if (this.fechaAcuerdo == null) {
            this.fechaAcuerdo = LocalDateTime.now();
        }
    }
}