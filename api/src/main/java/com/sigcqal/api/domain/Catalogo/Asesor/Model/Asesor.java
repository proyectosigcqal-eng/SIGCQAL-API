package com.sigcqal.api.domain.Catalogo.Asesor.Model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Asesor {
    private Long idAsesor;
    private Long idPersona;
    private String especialidad;
    private Integer cargaActual;
    private LocalDateTime ultimaAsignacionAt;
}
