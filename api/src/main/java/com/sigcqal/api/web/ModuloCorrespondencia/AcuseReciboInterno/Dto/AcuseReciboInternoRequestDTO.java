package com.sigcqal.api.web.ModuloCorrespondencia.AcuseReciboInterno.Dto;

import lombok.Data;

@Data
public class AcuseReciboInternoRequestDTO {
    private Long idAcuse;
    private Boolean esDelArea;

    private String fechaAceptacion;
    private String horaAceptacion;
    private Long idUsuarioRevisor;

    // 🔹 Memorandum
    private Long idMemorandum;
    private Long idCorrespondencia;
    private String numMemo;
    private String fechaEmision;
    private Long idUsuarioEmisor;
    private String folioUnico;
    private String observaciones;
    private String urlMemorandumGenerado;
    private Long idPlantilla;
    private Long idArea;
    private Long idUsuarioFirmante;
}