package com.sigcqal.api.web.Catalogo.Contribuyente.Dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContribuyenteDto {
    private Long id;
    private Integer idPersona;
    private Date fechaRegistroSistema;
    private String observacionesInternas;
}
