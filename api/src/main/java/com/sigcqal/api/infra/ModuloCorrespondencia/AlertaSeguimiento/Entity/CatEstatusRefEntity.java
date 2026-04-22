package com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Entity;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "cat_estatus")
@Immutable
@Data
public class CatEstatusRefEntity {
    @Id
    @Column(name = "id_estatus")
    private Long idEstatus;

    @Column(name = "nombre_estatus")
    private String nombreEstatus;
}

