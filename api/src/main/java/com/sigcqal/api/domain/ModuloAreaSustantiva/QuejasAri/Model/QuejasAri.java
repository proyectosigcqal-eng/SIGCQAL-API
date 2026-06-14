package com.sigcqal.api.domain.ModuloAreaSustantiva.QuejasAri.Model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuejasAri {
    
    private Long idAri;
    private Long idQueja;
    private Long idCir;
    private String numExpedienteOficial;
    private String sintesisActosOmisiones;
    private String articulosVulneradosAutoridad;
    private String nombreEncargadoFirma;
    private LocalDateTime fechaAcuerdo;
    private String rutaPdfAri;
    private Long idPlantillaQuejaAri;
    private String multasRequerimientos;
    private String multasCredito;
    private String instituto;
    // Campos adicionales para respuesta en las plantillas o vistas
    private String nombrePlantilla;
}