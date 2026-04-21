package com.sigcqal.api.domain.Catalogo.Estatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "cat_estatus") 
@Data
public class EstatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estatus")
    private Long idEstatus;

    @Column(name = "nombre_estatus")
    private String nombreEstatus;

    @Column(name = "descripcion")
    private String descripcion;

}