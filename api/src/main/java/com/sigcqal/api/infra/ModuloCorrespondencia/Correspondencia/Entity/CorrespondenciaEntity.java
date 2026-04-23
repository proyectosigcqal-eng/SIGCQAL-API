package com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Entity;

import java.time.LocalDate;

import com.sigcqal.api.infra.Catalogo.Area.Entity.AreaEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;

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
@Table(name = "correspondencia") 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorrespondenciaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_folio")
    private Long id;

    @Column(name = "folio_unico", unique = true)
    private String folioUnico;

    @Column(name = "num_oficio_externo")
    private String numeroOficio;

    @Column(name = "fecha_oficio")
    private LocalDate fechaExpedicion;

    @Column(name = "dependencia_remitente")
    private String dependenciaRemitente;

    @Column(name = "nombre_remitente")
    private String titularDependencia;

    @Column(name = "asunto")
    private String asunto;

    @Column(name = "fecha_recibido")
    private LocalDate fechaRecibido;

    @Column(name = "id_estatus")
    private Long idEstatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estatus", insertable = false, updatable = false)
    private CatEstatusEntity estatus;

    @Column(name = "id_usuario_captura")
    private Long idUsuarioCaptura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_captura", insertable = false, updatable = false)
    private UsuarioEntity usuarioCaptura;

    @Column(name = "observaciones_validacion")
    private String observaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area", insertable = false, updatable = false)
    private AreaEntity area;
}
