package com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoCorrespondencia.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.sigcqal.api.infra.Catalogo.Estatus.Entity.EstatusEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Entity.CatEstatusEntity;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seguimiento_correspondencia")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class SeguimientoCorrespondenciaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguimiento_correspondencia")
    private Long id;
    
    @Column(name = "id_folio")
    private Integer idFolio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_folio", insertable = false, updatable = false)
    private CorrespondenciaEntity correspondencia;

    @Column(name = "folio_respuesta", length = 50, nullable = false)
    private String folioRespuesta;

    @Column(name = "respuesta_seguimiento_correspondencia", columnDefinition = "TEXT")
    private String respuesta;

    @Column(name = "fecha_resolucion", nullable = false)
    private LocalDate fechaResolucion;

    @Column(name = "hora_resolucion", nullable = false)
    private LocalTime horaResolucion;

    @Column(name = "archivo_adjunto", length = 255)
    private String archivoAdjunto;

    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    private UsuarioEntity usuario;

    @Column(name = "id_estatus", nullable = false)
    private Integer idEstatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estatus", insertable = false, updatable = false)
    private EstatusEntity estatus;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

}
