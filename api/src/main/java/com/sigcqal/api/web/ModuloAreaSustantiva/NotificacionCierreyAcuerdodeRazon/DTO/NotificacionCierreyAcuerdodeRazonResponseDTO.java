package com.sigcqal.api.web.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.DTO;


import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class NotificacionCierreyAcuerdodeRazonResponseDTO {

    private Integer idCierre;
    private Integer idExpediente;
    private String medioNotificacion;
    private String rutaArchivoAcuerdo;
    private LocalDateTime fechaCierre;
    private Integer idUsuarioCierre;
    
}