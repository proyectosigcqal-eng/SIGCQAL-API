package com.sigcqal.api.domain.ModuloCorrespondencia.AcuseOficio.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcuseOficio {
    private Long idAcuseOficio;
    private Long idOficio;
    private Long idUsuarioRevisor;
    private LocalDate fechaAceptacion;
    private LocalTime horaAceptacion;
    private Boolean esDelArea;

    // Datos de Oficio
    private Long idCorrespondencia;
    private String numMemo;
    private String instruccionSeguimiento;
    private String fechaEmision; 
    private Long idUsuarioEmisor;
    private String folioUnico;
    private String observaciones;
    private String urlMemorandumGenerado;
    private Long idPlantilla;
    private Long idArea;
    private String nombreArea; // Se llenará con area.getNombre()
    private Long idUsuarioFirmante;
}