package com.sigcqal.api.domain.ModuloAreaSustantiva.CierreAutomatico.Model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CierreAutomatico {
     private Integer idExpediente;
    private String folioGobierno;
    private LocalDate fechaSolicitud;
    private LocalDate fechaLimite;
}
