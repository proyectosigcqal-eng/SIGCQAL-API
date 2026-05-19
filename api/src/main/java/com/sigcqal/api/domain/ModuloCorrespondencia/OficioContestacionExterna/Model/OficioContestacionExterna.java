package com.sigcqal.api.domain.ModuloCorrespondencia.OficioContestacionExterna.Model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OficioContestacionExterna {
    private Long idOficioContestacion;
    private Long idCorrespondencia;
    private Long idUsuarioEmisor;
    private String numOficioSalida;
    private String asuntoContestacion;
    private String cuerpoOficioTexto;
    private String urlPdfFinal;
    private LocalDateTime fechaEmision;
    private String folioCorrespondencia;
    private String asuntoCorrespondencia;
}
