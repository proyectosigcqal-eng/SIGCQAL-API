package com.sigcqal.api.web.ModuloAreaSustantiva.Expediente.DTO;

import java.time.LocalDateTime;

import lombok.Data;
@Data
public class ExpedienteRequestDTO {

    private String folioGobierno;
    private LocalDateTime fechaSolicitud;
    private Long idMunicipio;
    private Long idAsesor;
    private Long idTipoTramite;
    private Long idEstatusExpediente;
    private String documentoAcreditaPersonalidad;
    private String archivoDocumentoAcreditaPersonalidad;
    private Long idContribuyente;
    private Long idSolicitante;

}
