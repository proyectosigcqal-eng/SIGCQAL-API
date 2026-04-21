package com.sigcqal.api.domain.ModuloCorrespondencia.BitacoraHistorica.Model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BitacoraHistorica {
    private Long idLog; 
    private Long idCorrespondencia; 
    private Long idUsuarioAccion; 
    private Long estatusAnterior; 
    private Long estatusNuevo; 
    private String observaciones; 
    private LocalDateTime fechaMovimiento; 
    private String nombreUsuario;
    private String nombreEstatusAnterior;
    private String nombreEstatusNuevo;
    private String dependenciaRemitente;
    private String asuntoCorrespondencia;

}
