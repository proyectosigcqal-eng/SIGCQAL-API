package com.sigcqal.api.infra.ModuloCorrespondencia.OficioContestacionExterna.Entity;

import java.time.LocalDateTime;

import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Entity.CorrespondenciaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "oficio_contestacion_externa", schema = "correspondencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OficioContestacionExternaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_oficio_contestacion")
    private Long idOficioContestacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_correspondencia")
    private CorrespondenciaEntity correspondencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_emisor")
    private UsuarioEntity usuarioEmisor;

    @Column(name = "num_oficio_salida")
    private String numOficioSalida;

    @Column(name = "asunto_contestacion")
    private String asuntoContestacion;

    @Column(name = "cuerpo_oficio_texto", columnDefinition = "TEXT")
    private String cuerpoOficioTexto;

    @Column(name = "url_pdf_final")
    private String urlPdfFinal;

    @Column(name = "fecha_emision")
    private LocalDateTime fechaEmision;

    @PrePersist
    protected void onCreate() {
        if (this.fechaEmision == null) this.fechaEmision = LocalDateTime.now();
    }
}
