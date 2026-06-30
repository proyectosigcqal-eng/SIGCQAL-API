package com.sigcqal.api.web.ModuloAreaSustantiva.RLCir.Dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class RLCirRequestDTO {
    private Long idExpediente;
    private LocalDate fechaEmision;
    private String motivos;
    private String articulos;
    private String observaciones;
    private Long idAsesorRemitente;
    private Long idAsesorRecibe;
    private String director;
    private String rutaPdfRlCir;
    private String identificacionOficial; // Nuevo campo para la identificación oficial

    // Campos complementarios para enriquecer la plantilla Word
    private String folioGobierno;
    private String nombreContribuyente;
    private String nombreAsesorRemitente;
    private String nombreAsesorRecibe;
}