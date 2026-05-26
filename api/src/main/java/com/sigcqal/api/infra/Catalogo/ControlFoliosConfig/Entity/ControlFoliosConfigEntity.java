package com.sigcqal.api.infra.Catalogo.ControlFoliosConfig.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "control_folios_config", schema = "catalogos")
@Data
public class ControlFoliosConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_control_folios_config")
    private Long id;

    @Column(name = "nombre")
    private String nombre;
}
