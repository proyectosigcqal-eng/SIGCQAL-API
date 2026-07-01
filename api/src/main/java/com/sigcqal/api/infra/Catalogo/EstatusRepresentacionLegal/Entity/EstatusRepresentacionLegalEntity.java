package com.sigcqal.api.infra.Catalogo.EstatusRepresentacionLegal.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "estatus_representacion_legal", schema = "catalogos")
public class EstatusRepresentacionLegalEntity {

    @Id
    @Column(name = "id_estatus")
    private Long id;

    @Column(name = "nombre_estatus")
    private String nombreEstatus;
}
