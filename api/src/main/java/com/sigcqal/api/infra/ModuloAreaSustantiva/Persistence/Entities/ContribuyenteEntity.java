package com.sigcqal.api.infra.ModuloAreaSustantiva.Persistence.Entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;
import com.sigcqal.api.infra.Catalogo.Persona.Entity.PersonaEntity;

@Entity
@Table(name = "contribuyentes", schema = "sustantiva")
@Data
public class ContribuyenteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contribuyentes")
    private Long idContribuyentes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona", nullable = false)
    private PersonaEntity persona; // FK a la tabla de personas

    @Column(name = "fecha_registro_sistema")
    private LocalDateTime fechaRegistroSistema;

    @Column(name = "observaciones_internas", columnDefinition = "TEXT")
    private String observacionesInternas;

    @Column(name = "tipo_identificacion", columnDefinition = "TEXT")
    private String tipoIdentificacion;
}