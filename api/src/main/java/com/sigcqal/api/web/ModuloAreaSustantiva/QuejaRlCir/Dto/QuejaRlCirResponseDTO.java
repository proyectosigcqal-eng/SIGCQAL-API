package com.sigcqal.api.web.ModuloAreaSustantiva.QuejaRlCir.Dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QuejaRlCirResponseDTO {
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