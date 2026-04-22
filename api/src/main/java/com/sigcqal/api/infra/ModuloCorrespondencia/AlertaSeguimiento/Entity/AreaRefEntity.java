package com.sigcqal.api.infra.ModuloCorrespondencia.AlertaSeguimiento.Entity;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "areas")
@Immutable
@Data
public class AreaRefEntity {
    @Id
    @Column(name = "id_area")
    private Long idArea;

    @Column(name = "nombre_area")
    private String nombre;
}

