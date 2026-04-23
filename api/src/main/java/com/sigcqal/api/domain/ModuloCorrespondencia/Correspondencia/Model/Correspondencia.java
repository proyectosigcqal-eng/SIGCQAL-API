package com.sigcqal.api.domain.ModuloCorrespondencia.Correspondencia.Model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Correspondencia {
    private Long id;
    private Long consecutivo;
    private String folioUnico;
    private String numeroOficio;
    private LocalDate fechaExpedicion;
    private String dependenciaRemitente;
    private String titularDependencia;
    private String asunto;
    private LocalDate fechaRecibido;
    private Long idEstatus;
    private Long idUsuarioCaptura;
    private Long idArea;
    private String nombreArea;
    private String observaciones;

}
