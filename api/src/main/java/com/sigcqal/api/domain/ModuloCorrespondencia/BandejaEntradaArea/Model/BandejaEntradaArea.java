package com.sigcqal.api.domain.ModuloCorrespondencia.BandejaEntradaArea.Model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BandejaEntradaArea {
    private Long id;
    private Long idCorrespondencia;
    private Long idArea;
    private Long idUsuarioAsignado;
    private String estatus;
    private LocalDateTime fechaAsignacion;
    private String observaciones;
}

