package com.sigcqal.api.infra.Catalogo.Contribuyente.Entity;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Table(name = "contribuyentes", schema = "sustantiva")
@Data
public class ContribuyenteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contribuyentes")
    private Long id;

    @Column(name = "id_persona")
    private Long idPersona;

    @Column(name = "fecha_registro_sistema")
    private String fechaRegistroSistema;

    @Column(name = "observaciones_internas")
    private String observacionesInternas;
}
