package com.sigcqal.api.web.ModuloAreaSustantiva.BandejaAsesoria.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UltimaModificacionDto {
    private String descripcion;
    private String timestamp;
}
