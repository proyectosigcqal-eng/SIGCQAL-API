package com.sigcqal.api.web.ModuloAreaSustantiva.DemandaAmparo.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EncabezadoHitoAmparoDto {

    private Long idDemandaAmparo;
    private String folioGobierno;
    private String numExpedienteOficialQueja;
    private String nombreContribuyente;
}
