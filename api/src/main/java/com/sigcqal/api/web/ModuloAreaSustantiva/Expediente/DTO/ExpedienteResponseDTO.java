package com.sigcqal.api.web.ModuloAreaSustantiva.Expediente.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ExpedienteResponseDTO {
    private String folioGobierno;
    private LocalDateTime fechaSolicitud;
    private Long idMunicipio;
    private Long idAsesor;
    private Long idContribuyente;
    private Long idSolicitante;
    private Long idTipoTramite;
    private Long idEstatusExpediente;
    private String documentoAcreditaPersonalidad;
    private String archivoDocumentoAcreditaPersonalidad;
}
