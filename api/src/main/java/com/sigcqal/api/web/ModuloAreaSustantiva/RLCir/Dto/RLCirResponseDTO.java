package com.sigcqal.api.web.ModuloAreaSustantiva.RLCir.Dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class RLCirResponseDTO {
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
    private String nombrePlantilla;

    // Campos enriquecidos para la vista
    private String folioGobierno;
    private String nombreContribuyente;
}