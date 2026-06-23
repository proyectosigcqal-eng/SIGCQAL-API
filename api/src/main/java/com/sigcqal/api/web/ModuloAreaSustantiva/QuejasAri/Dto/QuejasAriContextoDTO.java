package com.sigcqal.api.web.ModuloAreaSustantiva.QuejasAri.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class QuejasAriContextoDTO {
    private Long idQueja;
    private Long idCir;
}