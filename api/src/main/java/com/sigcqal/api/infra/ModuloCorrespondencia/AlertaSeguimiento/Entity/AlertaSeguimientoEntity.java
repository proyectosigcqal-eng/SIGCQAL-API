package com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alertas_seguimiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertaSeguimientoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Long idAlerta;

    @Column(name = "id_turno")
    private Long idTurno;

    @Column(name = "id_usuario_emisor")
    private Long idUsuarioEmisor;

    @Column(name = "mensaje_urgencia")
    private String mensajeUrgencia;

    @Column(name = "fecha_alerta")
    private LocalDateTime fechaAlerta;

    @Column(name = "leida")
    private Boolean leida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_turno", insertable = false, updatable = false)
    private CorrespondenciaTurnoRefEntity turno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_emisor", insertable = false, updatable = false)
    private UsuarioRefEntity usuarioEmisor;
}

