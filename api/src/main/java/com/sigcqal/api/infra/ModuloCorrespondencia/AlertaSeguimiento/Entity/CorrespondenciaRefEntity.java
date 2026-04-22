package com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Entity;

import java.time.LocalDate;

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
@Table(name = "correspondencia")
@Immutable
@Data
public class CorrespondenciaRefEntity {
    @Id
    @Column(name = "id_folio")
    private Long idFolio;

    @Column(name = "folio_unico")
    private String folioUnico;

    @Column(name = "num_oficio_externo")
    private String numOficioExterno;

    @Column(name = "dependencia_remitente")
    private String dependenciaRemitente;

    @Column(name = "nombre_remitente")
    private String nombreRemitente;

    @Column(name = "asunto")
    private String asunto;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @Column(name = "id_estatus")
    private Long idEstatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estatus", insertable = false, updatable = false)
    private CatEstatusRefEntity estatus;
}

