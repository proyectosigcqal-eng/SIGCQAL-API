package com.sigcqal.api.infra.Catalogo.Municipio.Entity;

import lombok.Data;

import com.sigcqal.api.infra.Catalogo.Estado.Entity.EstadoEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "cat_municipios" , schema = "catalogos") 
@Data
public class MunicipioEntity {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_municipio")
    private Long id;

    @Column(name = "nombre_municipio")
    private String nombreMunicipio;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "id_estado")
    private EstadoEntity estado;

}