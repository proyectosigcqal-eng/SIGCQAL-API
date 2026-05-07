package com.sigcqal.api.web.ModuloCorrespondencia.Memorandum.Dto;

import lombok.Data;
@Data
public class MemorandumRequestDTO {
    private Long idCorrespondencia;
    private String instruccionSeguimiento;
    private String observaciones;
    private Long idUsuarioEmisor;
    private Long idUsuarioFirmante;
    private Long idPlantilla;
    private Long idArea;
    private String folioUnico;
    private String urlSolicitudMemorandum;
    private String nombreEmisor;

    // ← Agregar estos — vienen del front cuando selecciona el firmante y área
    private String areaDestinatario;   // nombre del área destinataria
    private String nombreFirmante;     // nombre completo del firmante
    private String areaFirmante;       // área del firmante
}