package com.sigcqal.api.web.Catalogo.Contribuyente.Dto;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContribuyenteDto {
    private Long id;
    private Integer idPersona;
    private LocalDateTime fechaRegistroSistema;
    private String observacionesInternas;
}
