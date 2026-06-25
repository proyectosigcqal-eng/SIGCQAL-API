package com.sigcqal.api.web.Catalogo.Asesor.Dto;

import lombok.Data;

@Data
public class AsesorDTO {
    private Long    idAsesor;
    private Long    idPersona;
    private String  especialidad;
    private Integer cargaActual;
    private String  ultimaAsignacionAt;
    private String  nombre;
    private String  apellidoPaterno;  
    private String  apellidoMaterno;    
    private String  nombreCompleto;
    private String  correo;
    private String   telefono;
    private String  rfc;                      
    private Boolean activo;
         
}