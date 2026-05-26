package com.sigcqal.api.infra.Catalogo.CatAutoridad.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "cat_autoridades", schema = "catalogos")
@Data
public class CatAutoridadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cat_autoridad")
    private Long id;

    @Column(name = "nombre")
    private String nombre;
}
