package com.sigcqal.api.infra.Catalogo;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "cat_estados")
@Data
public class EstadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private Integer idEstado;

    @Column(name = "nombre_estado")
    private String nombreEstado;
}