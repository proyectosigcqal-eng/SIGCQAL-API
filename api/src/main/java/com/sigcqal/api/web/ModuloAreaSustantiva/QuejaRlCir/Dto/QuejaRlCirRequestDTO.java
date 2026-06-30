package com.sigcqal.api.web.ModuloAreaSustantiva.QuejaRlCir.Dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class QuejaRlCirRequestDTO {
    private Long idResolucionFinal;
    private LocalDate fechaEmision;
    private String motivos;
    private String articulos;
    private String observaciones;
    private String oficio;
    private Long idAsesorRemitente;
    private Long idAsesorRecibe;
    private String director;
}