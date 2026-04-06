package com.sigcqal.api.domain.MAIQR_CU_18;

import java.time.LocalDateTime;

public class Expediente {
    private Long id;
    private String folio; 
    private LocalDateTime fechaCreacion;
    private String estatus; 

    public boolean esFolioValido() {
        return folio != null && folio.matches("\\d{9}");
    }
}