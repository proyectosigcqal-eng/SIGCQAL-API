package com.sigcqal.api.domain.MAIQR_CU_22;

import java.time.LocalDateTime;


public class InteraccionInstitucional {
    private Long id;
   private String folioExpediente;
    private String dependenciaDestino;
    private String encargadoDependencia;
    private String asesorResponsable;
    private String administradorRegistra;
    private String numeroOficio;
    private String asuntoContestacion;
    private Long estatusId; 
    private String rutaArchivoAcuse; 
    private LocalDateTime fechaGeneracionFormato;
    private LocalDateTime fechaCargaAcuse;

    public static final Long ESTATUS_ESPERANDO_RESPUESTA = 1L;
    public static final Long ESTATUS_FORMATO_DESCARGADO = 2L;
    public static final Long ESTATUS_ATENDIDO = 3L;

    public void finalizarConAcuse(String rutaArchivo) {
        if (ESTATUS_FORMATO_DESCARGADO.equals(this.estatusId)) {
            this.rutaArchivoAcuse = rutaArchivo;
            this.fechaCargaAcuse = LocalDateTime.now();
            this.estatusId = ESTATUS_ATENDIDO;
        } else {
            throw new IllegalStateException("No se puede cargar acuse si el formato no ha sido descargado.");
        }
    }
}
