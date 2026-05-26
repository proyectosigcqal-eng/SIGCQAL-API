package com.sigcqal.api.infra.Catalogo.EstatusExpediente.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "estatus_expediente", schema = "catalogos")
@Data
public class EstatusExpedienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estatus_expediente")
    private Long id;

    @Column(name = "nombre")
    private String nombre;
}
