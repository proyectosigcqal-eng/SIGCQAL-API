package com.sigcqal.api.infra.Expediente.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "contribuyentes", schema = "sustantiva")
@Data
public class ContribuyenteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contribuyente")
    private Long id;

    @Column(name = "rfc")
    private String rfc;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "id_direccion")
    private Integer idDireccion;

    @Column(name = "correo_electronico")
    private String correoElectronico;

    @Column(name = "telefono")
    private String telefono;
}
