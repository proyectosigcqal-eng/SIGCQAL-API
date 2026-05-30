package com.sigcqal.api.web.Catalogo.Asesor.Dto;

import lombok.Data;

@Data
public class AsesorDTO {
    private Long idAsesor;
    private Long idPersona;
    private String especialidad;
    private Integer cargaActual;
    private String ultimaAsignacionAt;
}
