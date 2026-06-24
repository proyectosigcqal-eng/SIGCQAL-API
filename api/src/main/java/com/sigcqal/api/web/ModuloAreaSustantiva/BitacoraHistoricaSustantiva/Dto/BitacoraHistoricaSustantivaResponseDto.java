package com.sigcqal.api.web.ModuloAreaSustantiva.BitacoraHistoricaSustantiva.Dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

/**
 * DTO que representa un evento único en la bitácora histórica.
 * Este objeto es el que recibirá el frontend para mostrar la información al usuario.
 */
@Data
@Builder
public class BitacoraHistoricaSustantivaResponseDto {

    private String tipoEvento;
    private LocalDateTime fecha;
    private String autorCompleto;
    private String descripcion;
    private String estatus;
    private String fuente;
    private String rutaArchivo; 
}