package com.sigcqal.api.infra.Catalogo.TipoPersona.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tipo_persona", schema = "catalogos")
@Data
public class TipoPersonaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_persona")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

}
