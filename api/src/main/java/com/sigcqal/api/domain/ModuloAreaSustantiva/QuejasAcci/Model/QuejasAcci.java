package com.sigcqal.api.domain.ModuloAreaSustantiva.QuejasAcci.Model;


import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class QuejasAcci {
    private Long          id;
    private Long          idQueja;
    private Long          idOficioAutoridad;
    private String        justificacionInvestigacion;
    private String        nuevosRequerimientosAutoridad;
    private Integer       plazoDiasHabiles;
    private LocalDateTime fechaEmisionAcci;
    private String        rutaPdfAcci;
    private Boolean       concluido;
    private LocalDateTime fechaConclusion;
}