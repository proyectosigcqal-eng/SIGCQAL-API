package com.sigcqal.api.domain.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
public class NotificacionCierreyAcuerdodeRazon {
    private Integer idCierre;
    private Integer idExpediente;
    private String medioNotificacion;
    private String rutaArchivoAcuerdo;
    private LocalDateTime fechaCierre;
    private Integer idUsuarioCierre;
}
