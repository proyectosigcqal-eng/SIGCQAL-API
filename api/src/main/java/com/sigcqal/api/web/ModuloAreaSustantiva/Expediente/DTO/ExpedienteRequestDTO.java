package com.sigcqal.api.web.ModuloAreaSustantiva.Expediente.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    
    private ContribuyenteDatosDTO contribuyenteDatos;
    private SolicitanteDatosDTO solicitanteDatos;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContribuyenteDatosDTO {
        private String nombre;
        private String apellidoPaterno;
        private String apellidoMaterno;
        private String rfc;
        private String curp;
        private String telefono;
        private String telefonoFijo;
        private String correo;
        private String comunidad;
        private String rec;
        private String identificacionOficial;
        private String numeroIdFolio;
        private Long idDireccion;
        private Long idTipoPersona;
        private String observacionesInternas;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SolicitanteDatosDTO {
        private String nombre;
        private String apellidoPaterno;
        private String apellidoMaterno;
        private String rfc;
        private String curp;
        private String telefono;
        private String telefonoFijo;
        private String correo;
        private String comunidad;
        private String rec;
        private String identificacionOficial;
        private String numeroIdFolio;
        private Long idDireccion;
        private Long idTipoPersona;
    }
}
