package com.sigcqal.api.infra.Catalogo.Estatus.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cat_estatus" , schema = "catalogos")
@Data
public class EstatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estatus")
    private Integer idEstatus;

    @Column(name = "nombre_estatus")
    private String nombreEstatus;

    @Column(name = "descripcion")
    private String descripcion;
}
