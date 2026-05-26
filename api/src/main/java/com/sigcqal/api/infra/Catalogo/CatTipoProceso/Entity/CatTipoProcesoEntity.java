package com.sigcqal.api.infra.Catalogo.CatTipoProceso.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "cat_tipo_proceso", schema = "catalogos")
@Data
public class CatTipoProcesoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cat_tipo_proceso")
    private Long id;

    @Column(name = "nombre")
    private String nombre;
}
