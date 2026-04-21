package com.sigcqal.api.infra.ModuloCorrespondencia.BitacoraHistorica.Entity;

import java.time.LocalDateTime;

import com.sigcqal.api.domain.Catalogo.Estatus.EstatusEntity;
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
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "auditoria_correspondencia") 
@Data
public class BitacoraHistoricaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log")
    private Long idLog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_correspondencia")
    private CorrespondenciaEntity correspondencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_accion")
    private UsuarioEntity usuarioAccion;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_anterior" )
    private EstatusEntity estatusAnterior;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_nuevo" )
    private EstatusEntity estatusNuevo;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "fecha_movimiento")
    private LocalDateTime fechaMovimiento;
}