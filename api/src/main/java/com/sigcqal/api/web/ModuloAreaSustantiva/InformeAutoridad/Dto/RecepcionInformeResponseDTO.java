package com.sigcqal.api.web.ModuloAreaSustantiva.InformeAutoridad.Dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecepcionInformeResponseDTO {
    @JsonProperty("id_registro_informe")
    private Integer idRegistroInforme;

    @JsonProperty("estatus_expediente")
    private String estatusExpediente;

    @JsonProperty("numero_oficio_respuesta")
    private String numeroOficioRespuesta;

    @JsonProperty("fecha_registro")
    private LocalDateTime fechaRegistro;

    @JsonProperty("ruta_archivo_pdf")
    private String rutaArchivoPdf;

    private String mensaje;

    @JsonProperty("puede_notificar")
    private Boolean puedeNotificar;
}

