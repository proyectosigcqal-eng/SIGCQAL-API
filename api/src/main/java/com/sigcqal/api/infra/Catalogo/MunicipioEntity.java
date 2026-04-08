package com.sigcqal.api.infra.Catalogo;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "cat_municipios") 
@Data
public class MunicipioEntity {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_municipio")
    private Long id;

    @Column(name = "nombre_municipio")
    private String nombreMunicipio;

    @Column(name = "id_estado")
    private Integer idEstado;

}