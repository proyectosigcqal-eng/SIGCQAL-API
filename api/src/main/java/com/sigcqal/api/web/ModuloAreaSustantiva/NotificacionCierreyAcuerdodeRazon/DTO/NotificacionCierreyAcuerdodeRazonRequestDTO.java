package com.sigcqal.api.web.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.DTO;

import lombok.Data;

@Data
public class NotificacionCierreyAcuerdodeRazonRequestDTO {
    private Integer idCierre;
    private Integer idExpediente;
    private String medioNotificacion;
    private String rutaArchivoAcuerdo;
    private Integer idUsuarioCierre;

}
