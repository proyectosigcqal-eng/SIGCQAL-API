package com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoMemorandum.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.sigcqal.api.infra.Catalogo.Estatus.Entity.EstatusEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.Memorandum.Entity.MemorandumEntity;

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
@Table(name = "seguimiento_memorandum")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeguimientoMemorandumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguimiento_memorandum")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_memo")
    private MemorandumEntity memorandum;

    @Column(name = "folio_respuesta", insertable = false, updatable = false)
    private Integer folioRespuesta;

    @Column(name = "respuesta_seguimiento_memorandum", columnDefinition = "TEXT")
    private String respuestaSeguimientoMemorandum;

    @Column(name = "fecha_resolucion")
    private LocalDate fechaResolucion;

    @Column(name = "hora_resolucion")
    private LocalTime horaResolucion;

    @Column(name = "archivo_adjunto", length = 255)
    private String archivoAdjunto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estatus")
    private EstatusEntity estatus;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void onCreate() {
        if (this.fechaRegistro == null) {
            this.fechaRegistro = LocalDateTime.now();
        }
    }
}
