package com.sigcqal.api.domain.Expediente.Model;

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
    private String rfc;
    private String razonSocial;
    private Integer idDireccion;
    private String correoElectronico;
    private String telefono;
}
