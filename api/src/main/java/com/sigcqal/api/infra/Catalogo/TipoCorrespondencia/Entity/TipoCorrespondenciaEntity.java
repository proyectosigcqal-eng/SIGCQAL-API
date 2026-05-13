package com.sigcqal.api.infra.Catalogo.TipoCorrespondencia.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Table(name = "tipo_correspondencia", schema = "catalogos")
@Data
public class TipoCorrespondenciaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_correspondencia")
    private Integer idTipo;

    @Transient
    private String idNatural;

    @Column(name = "descripcion")
    private String descripcion;

    public String getIdNatural() {
        if (idNatural != null) return idNatural;
        if (descripcion == null) return null;
        return descripcion.toUpperCase();
    }
}
