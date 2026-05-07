package com.sigcqal.api.web.ModuloCorrespondencia.AcuseOficio.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AcuseOficioResponseDTO {
    private Long idAcuseOficio;
    private Boolean esDelArea;
    private String fechaAceptacion;
    private String horaAceptacion;
    private Long idUsuarioRevisor;

    // Campos de Oficio
    private Long idOficio;
    private Long idCorrespondencia;
    private String numOficio;
    private String instruccionSeguimiento;
    private String fechaEmision;
    private Long idUsuarioEmisor;
    private String folioUnico;
    private String observaciones;
    private String urlMemorandumGenerado;
    private Long idPlantilla;
    private Long idArea;
    private String nombreArea;
    private Long idUsuarioFirmante;
}