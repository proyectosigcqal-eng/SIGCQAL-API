package com.sigcqal.api.web.ModuloAreaSustantiva.RegistroContribuyente.Dto;

import lombok.Data;

@Data
public class RegistroContribuyenteRequestDTO {
    // Datos del Expediente
    private String folioGobierno;
    private Long idContribuyente;
    private Long idSolicitante;
    private Long idMunicipio;
    private Long idAsesor;
    
    // Datos de la Asesoría (Detalle)
    private String problematica;
    private String seguimiento;
    private String calificacionActo;
    private Double monto;
}