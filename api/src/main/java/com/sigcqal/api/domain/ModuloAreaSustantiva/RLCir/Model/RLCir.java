package com.sigcqal.api.domain.ModuloAreaSustantiva.RLCir.Model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RLCir {
    private Long idRlCir;
    private Long idExpediente;
    private LocalDateTime fechaEmision;
    private String motivos;
    private String articulos;
    private String observaciones;
    private Long idAsesorRemitente;
    private Long idAsesorRecibe;
    private String director;
    private String rutaPdfRlCir;
}