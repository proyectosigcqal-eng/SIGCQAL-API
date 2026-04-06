package com.sigcqal.api.domain.MC_CU_04;

import java.time.LocalDateTime;

public class Memorandum {
    private Long id;
    private String folioMemorandum;
    private String folioCorrespondenciaOrigen;
    private String instrucciones;
    private String emisorNombre; // El administrador que lo genera
    private LocalDateTime fechaEmision;

    // Atributos de la plantilla
    private String contenidoHtml; // Para previsualización
}
