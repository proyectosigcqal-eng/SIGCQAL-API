package com.sigcqal.api.web.ModuloAreaSustantiva.QuejasAri.Dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class QuejasAriRequestDTO {
    private Long idQueja;
    private Long idCir;
    private String numExpedienteOficial;
    private String sintesisActosOmisiones;
    private String nombreEncargadoFirma;
    private LocalDateTime fechaAcuerdo;
    private String rutaPdfAri;
    private Long idPlantillaQuejaAri;
    private String multasRequerimientos;
    private String multasCredito;
    private String instituto;
}