package com.sigcqal.api.web.ModuloAreaSustantiva.QuejasAri.Dto;

import java.time.LocalDate;
import lombok.Data; // 👈 Asegúrate de tener esta importación

@Data // 👈 Esta anotación genera automáticamente todos los Getters y Setters en tiempo de compilación
public class QuejasAriRequestDTO {
    
    private Long idQueja;
    private Long idCir;
    private String numExpedienteOficial;
    private String sintesisActosOmisiones;
    private String nombreEncargadoFirma;
    private LocalDate fechaAcuerdo;
    private Long idPlantillaQuejaAri;
    private String multasRequerimientos;
    private String instituto;
    private String multasCredito;
    private String rutaPdfAri;

    // Campos enriquecidos
    private String folioGobierno;
    private String nombreAsesor;
    private String rfcAsesor;
    private String nombreRepresentante;
    private String nombreContribuyente;
    private String identificacionContribuyente;
    private String fechaSolicitud;
}