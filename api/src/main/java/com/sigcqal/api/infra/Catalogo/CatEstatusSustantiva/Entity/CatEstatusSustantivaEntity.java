package com.sigcqal.api.infra.Catalogo.CatEstatusSustantiva.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "cat_estatus_sustantiva", schema = "catalogos")
@Data
public class CatEstatusSustantivaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cat_estatus_sustantiva")
    private Long id;

    @Column(name = "nombre")
    private String nombre;
}
