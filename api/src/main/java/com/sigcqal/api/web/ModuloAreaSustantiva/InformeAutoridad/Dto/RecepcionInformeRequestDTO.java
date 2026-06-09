package com.sigcqal.api.web.ModuloAreaSustantiva.InformeAutoridad.Dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecepcionInformeRequestDTO {
    private Integer expedienteId;
    private String numeroOficioRespuesta;
    private Integer foliosCantidad;
    private LocalDateTime fechaRecepcion;
    private String nombreArchivoPdf;
    private String observaciones;
    private String ipCliente;
}

