package com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Model;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contribuyente {
    private Long id;
    private Integer idPersona;
    private LocalDateTime fechaRegistroSistema;
    private String observacionesInternas;
    
}
