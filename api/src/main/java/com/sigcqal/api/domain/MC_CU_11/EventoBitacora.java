package com.sigcqal.api.domain.MC_CU_11;

import java.time.LocalDateTime;

public class EventoBitacora {
    private Long id;
    private String folioRelacionado;
    private String accion; // REGISTRO, EDICION, DESCARGA_FORMATO, CARGA_ACUSE
    private String usuarioResponsable;
    private String ipOrigen;
    private LocalDateTime fechaEvento;
    private String detalles; // JSON o texto descriptivo de lo que cambió
}
