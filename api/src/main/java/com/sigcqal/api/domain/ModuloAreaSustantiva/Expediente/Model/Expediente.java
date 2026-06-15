package com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expediente {
    private Integer id;
    private String folioGobierno;
    private LocalDateTime fechaSolicitud;
    private Long idMunicipio;
    private Long idAsesor;
    private Long idContribuyente;
    private Long idSolicitante;
    private Long idRepresentanteLegal;
    private Long idTipoTramite;
    private Long idEstatusExpediente;
    private String documentoAcreditaPersonalidad;
    private String archivoDocumentoAcreditaPersonalidad;
    private Boolean bloqueado;
    private java.time.LocalDateTime fechaCierreAutomatico;

}
