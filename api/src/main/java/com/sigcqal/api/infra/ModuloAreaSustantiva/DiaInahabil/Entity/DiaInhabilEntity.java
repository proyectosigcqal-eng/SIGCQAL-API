package com.sigcqal.api.infra.ModuloAreaSustantiva.DiaInahabil.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity(name = "DiaInhabilEntity")
@Table(name = "dias_inhabiles", schema = "catalogos")
@Data
public class DiaInhabilEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dia_inhabil")
    private Integer id;

    @Column(name = "fecha", nullable = false, unique = true)
    private LocalDate fecha;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "activo")
    private Boolean activo;
}