package com.sigcqal.api.infra.Catalogo.Asesor.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(name = "id_persona")
    private Long idPersona;

    @Column(name = "especialidad")
    private String especialidad;

    @Column(name = "carga_actual")
    private Integer cargaActual;

    @Column(name = "ultima_asignacion_at")
    private LocalDateTime ultimaAsignacionAt;
}
