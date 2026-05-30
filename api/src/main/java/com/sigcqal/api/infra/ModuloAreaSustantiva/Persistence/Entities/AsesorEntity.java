package com.sigcqal.api.infra.ModuloAreaSustantiva.Persistence.Entities;


import com.sigcqal.api.infra.Catalogo.Persona.Entity.PersonaEntity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "asesores", schema = "sustantiva")
@Data
public class AsesorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asesores", nullable = false)
    private Long idAsesores;

    // Columna para guardar el ID manualmente si es necesario
    @Column(name = "id_persona", nullable = false)
    private Long idPersona;

    // Relación definida con JPA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona", insertable = false, updatable = false)
    private PersonaEntity persona;

    @Column(name = "especialidad", length = 100)
    private String especialidad;

    @Column(name = "carga_actual")
    private Integer cargaActual;

    @Column(name = "ultima_asignacion_at")
    private LocalDateTime ultimaAsignacionAt;
}