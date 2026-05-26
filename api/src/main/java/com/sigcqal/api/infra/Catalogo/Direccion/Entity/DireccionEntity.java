package com.sigcqal.api.infra.Catalogo.Direccion.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "direcciones", schema = "catalogos")
@Data
public class DireccionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion")
    private Long id;

    @Column(name = "calle")
    private String calle;

    @Column(name = "num_ext")
    private String numExt;

    @Column(name = "num_int")
    private String numInt;

    @Column(name = "colonia")
    private String colonia;

    @Column(name = "cp")
    private String cp;

    @Column(name = "id_municipio")
    private Integer idMunicipio;

    @Column(name = "id_estado")
    private Integer idEstado;
}
