package com.sigcqal.api.infra.Catalogo.TipoEntrada.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tipo_entrada", schema = "catalogos")
@Data
public class TipoEntradaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_entrada")
    private Long id;

    @Column(name = "nombre")
    private String nombre;
}
