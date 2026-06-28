package com.sigcqal.api.domain.Catalogo.Personal.Model;

import com.sigcqal.api.domain.Catalogo.Persona.Model.Persona;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Personal {
    private Long idPersonal;
    private Persona persona; 
    private LocalDateTime fechaRegistro;
    private Boolean activo;

}
