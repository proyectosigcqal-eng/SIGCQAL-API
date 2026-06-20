package com.sigcqal.api.web.ModuloAreaSustantiva.QuejasAcci.Dto;


import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class QuejasAcciResponseDTO {
    private Long          id;
    private Long          idQueja;
    private String        rutaPdfAcci;
    private String        url;
    private LocalDateTime fechaEmisionAcci;
    private Boolean       concluido;
}