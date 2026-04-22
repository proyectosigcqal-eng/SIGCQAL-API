package com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Entity;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "correspondencia_turnos")
@Immutable
@Data
public class CorrespondenciaTurnoRefEntity {
    @Id
    @Column(name = "id_turno")
    private Long idTurno;

    @Column(name = "id_folio")
    private Long idFolio;

    @Column(name = "id_area_destino")
    private Long idAreaDestino;

    @Column(name = "id_usuario_responsable")
    private Long idUsuarioResponsable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area_destino", insertable = false, updatable = false)
    private AreaRefEntity areaDestino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_folio", insertable = false, updatable = false)
    private CorrespondenciaRefEntity correspondencia;
}

