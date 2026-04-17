package com.sigcqal.api.web.ModuloCorrespondencia.AcuseReciboInterno.Dto;

import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseReciboInterno.Model.AcuseReciboInterno;

import lombok.Data;

@Data
public class AcuseReciboInternoResponseDTO {

    private Long idAcuse;
    private Boolean esDelArea;

    private String fechaAceptacion;
    private String horaAceptacion;

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

    public static AcuseReciboInternoResponseDTO fromDomain(AcuseReciboInterno d) {

        AcuseReciboInternoResponseDTO dto = new AcuseReciboInternoResponseDTO();

        dto.setIdAcuse(d.getIdAcuse());
        dto.setEsDelArea(d.getEsDelArea());

        dto.setFechaAceptacion(
            d.getFechaAceptacion() != null ? d.getFechaAceptacion().toString() : null
        );

        dto.setHoraAceptacion(
            d.getHoraAceptacion() != null ? d.getHoraAceptacion().toString() : null
        );

        dto.setIdMemorandum(d.getIdMemorandum());
        dto.setIdCorrespondencia(d.getIdCorrespondencia());
        dto.setNumMemo(d.getNumMemo());
        dto.setFechaEmision(d.getFechaEmision());
        dto.setIdUsuarioEmisor(d.getIdUsuarioEmisor());
        dto.setFolioUnico(d.getFolioUnico());
        dto.setObservaciones(d.getObservaciones());
        dto.setUrlMemorandumGenerado(d.getUrlMemorandumGenerado());
        dto.setIdPlantilla(d.getIdPlantilla());
        dto.setIdArea(d.getIdArea());
        dto.setIdUsuarioFirmante(d.getIdUsuarioFirmante());

        return dto;
    }
}