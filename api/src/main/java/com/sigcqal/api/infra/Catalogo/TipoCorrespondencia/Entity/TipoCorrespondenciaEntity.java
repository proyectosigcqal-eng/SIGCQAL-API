package com.sigcqal.api.infra.Catalogo.TipoCorrespondencia.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "cat_tipo_correspondencia", schema = "catalogos")
@Data
public class TipoCorrespondenciaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo")
    private Integer idTipo;

    @Column(name = "id_natural", unique = true)
    private String idNatural;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "activo")
    private Boolean activo;
}
