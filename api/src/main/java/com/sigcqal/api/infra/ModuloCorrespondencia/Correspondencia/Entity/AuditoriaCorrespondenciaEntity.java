package com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "auditoria_correspondencia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditoriaCorrespondenciaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log")
    private Long id;

    @Column(name = "id_correspondencia")
    private Long idFolio;

    @Column(name = "id_usuario_accion")
    private Long idUsuarioAccion;

    @Column(name = "id_estado_anterior")
    private Long idEstadoAnterior;

    @Column(name = "id_estado_nuevo")
    private Long idEstadoNuevo;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "fecha_movimiento")
    private LocalDateTime fechaMovimiento;
}
