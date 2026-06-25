package com.sigcqal.api.infra.Catalogo.Asesor.Entity;

import java.time.LocalDateTime;

import com.sigcqal.api.infra.Catalogo.Persona.Entity.PersonaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "asesores", schema = "sustantiva")
@Data
public class AsesorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asesores")
    private Long idAsesor;
    @ManyToOne
    @JoinColumn(name = "id_persona", insertable = false, updatable = false)
    private PersonaEntity persona;

    @Column(name = "id_persona")
    private Long idPersona;

    @Column(name = "especialidad")
    private String especialidad;

    @Column(name = "carga_actual")
    private Integer cargaActual;

    @Column(name = "ultima_asignacion_at")
    private LocalDateTime ultimaAsignacionAt;
        @Column(name = "activo")
    private Boolean activo = true;
    }
