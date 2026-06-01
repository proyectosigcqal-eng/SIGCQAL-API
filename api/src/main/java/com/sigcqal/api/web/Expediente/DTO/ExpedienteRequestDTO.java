package com.sigcqal.api.web.Expediente.DTO;

import java.sql.Date;
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
    private ContribuyenteDatosDTO contribuyente;

    private Long idSolicitante;
    private SolicitanteDatosDTO solicitante;

    private Integer tipoActo;
    private Integer idAutoridad;
    private Integer idEstatusDetalleExpediente;
    private Integer idTipoEntrada;
    private String calificacionActo;
    private String problematica;
    private String seguimientoAsesoria;
    private Integer monto;
    private Date fechaNotificacion;
    private String nombreAutoridad;
    private String nombreTipoActo;
    private String nombreEstatusDetalle;
    private String nombreTipoEntrada;

    @Data
    public static class ContribuyenteDatosDTO {
        private String rfc;
        private String razonSocial;
        private Integer idDireccion;
        private String correoElectronico;
        private String telefono;
    }

    @Data
    public static class SolicitanteDatosDTO {
        private Integer idDireccion;
        private String nombre;
        private String apellidoPaterno;
        private String apellidoMaterno;
        private String curp;
        private String telefono;
    }
}
