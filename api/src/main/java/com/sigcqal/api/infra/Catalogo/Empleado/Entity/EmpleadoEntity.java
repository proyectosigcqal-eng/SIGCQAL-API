package com.sigcqal.api.infra.Catalogo.Empleado.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "empleados", schema = "catalogos")
@Data
public class EmpleadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Long id;

    @Column(name = "nombre_completo")
    private String nombreCompleto;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "id_area")
    private Integer idArea;
}
