package com.sigcqal.api.domain.ModuloAreaSustantiva.Queja.Model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Queja {
    private Integer idQueja;
    private Integer idExpediente;
    private Long idAsesor;
    private Long idAutoridad;
    private Long idEstatusQueja;
    private Boolean requisitoIdentificacion;
    private Boolean requisitoActosFiscales;
    private Boolean requisitoNarrativaClara;
    private Boolean requisitoCompetenciaCedecon;
    private LocalDateTime fechaRegistro;
    private LocalDateTime ultimaActualizacion;
}