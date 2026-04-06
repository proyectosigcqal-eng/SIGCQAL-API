package com.sigcqal.api.infra.MAIQR_CU_28;

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
public class ConvenioEntity {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_municipio")
    private Long id;

    @Column(name = "nombre_municipio")
    private String nombreMunicipio;

    @Column(name = "vigente")
    private boolean vigente; 
   
}

