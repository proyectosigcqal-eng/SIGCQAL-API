package com.sigcqal.api.domain.ModuloCorrespondencia.AcuseCorrespondencia.Model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcuseCorrespondencia {

    private Long idAcuseCorrespondencia;
    private Long idCorrespondencia;
    private Long idUsuarioRevisor;

    private LocalDate fechaAceptacion;
    private LocalTime horaAceptacion;
    private Boolean esDelArea;


    private String folioUnico;
    private String numeroOficio;
    private String dependenciaRemitente;
    private String titularDependencia;
    private String asunto;
    private String fechaExpedicion;
    private String fechaRecibido;
    private String observaciones;
    private Long idArea;
}