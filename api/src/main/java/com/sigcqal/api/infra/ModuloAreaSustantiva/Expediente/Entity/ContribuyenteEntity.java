package com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity;

import java.sql.Date;
import java.time.LocalDateTime;

import com.sigcqal.api.infra.Catalogo.Persona.Entity.PersonaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "contribuyentes", schema = "sustantiva")
@Data
public class ContribuyenteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contribuyentes")
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "id_persona")
    private PersonaEntity persona;

    @Column(name = "fecha_registro_sistema")
    private LocalDateTime fechaRegistroSistema;

    @Column(name = "observaciones_internas")
    private String observacionesInternas;

}
