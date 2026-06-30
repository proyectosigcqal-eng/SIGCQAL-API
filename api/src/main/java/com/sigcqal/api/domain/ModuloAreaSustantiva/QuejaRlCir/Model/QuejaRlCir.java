package com.sigcqal.api.domain.ModuloAreaSustantiva.QuejaRlCir.Model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuejaRlCir {
    private Long idQuejaRlCir;
    private Long idResolucionFinal;
    private LocalDateTime fechaEmision;
    private String motivos;
    private String articulos;
    private String observaciones;
    private String oficio;
    private Long idAsesorRemitente;
    private Long idAsesorRecibe;
    private String director;
    private String rutaPdfQuejaRlCir;
}