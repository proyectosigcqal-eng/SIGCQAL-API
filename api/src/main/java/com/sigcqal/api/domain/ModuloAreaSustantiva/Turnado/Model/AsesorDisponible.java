package com.sigcqal.api.domain.ModuloAreaSustantiva.Turnado.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AsesorDisponible {
    private Long   idAsesor;
    private String nombreCompleto;
    private Integer cargaActual;        
    private LocalDateTime ultimaAsignacion;
}