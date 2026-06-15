package com.sigcqal.api.web.ModuloAreaSustantiva.Queja.DTO;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QuejaResponseDTO {
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