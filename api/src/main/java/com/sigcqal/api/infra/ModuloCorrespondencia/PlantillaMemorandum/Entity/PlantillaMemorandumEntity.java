package com.sigcqal.api.infra.ModuloCorrespondencia.PlantillaMemorandum.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "plantilla_memorandum" , schema = "correspondencia")
@Data 
@NoArgsConstructor
@AllArgsConstructor
public class PlantillaMemorandumEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plantilla")
    private Long id;

    @Column(name = "nombre_plantilla")
    private String nombrePlantilla;

    @Column(name = "url_plantilla_memorandum")
    private String urlPlantilla;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "activo")
    private Boolean activo;

     
}
