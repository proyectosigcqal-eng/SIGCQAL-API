package com.sigcqal.api.application.ModuloAreaSustantiva.ControlPlazosAutoridad;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import com.sigcqal.api.web.ModuloAreaSustantiva.ControlPlazosAutoridad.Dto.RegistroInformeAutoridadResponseDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.ControlPlazosAutoridad.Dto.SemaforoAutoridadResponseDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.ControlPlazosAutoridad.Dto.SemaforoEstadoEnum;

@Component
public class ControlPlazosAutoridadMapper {

    public SemaforoAutoridadResponseDTO toSemaforoResponse(
            ExpedienteEntity expediente,
            SemaforoEstadoEnum estado,
            Integer diasHabilesRestantes,
            LocalDate fechaLimiteInforme,
            Boolean vencido) {

        return SemaforoAutoridadResponseDTO.builder()
                .expedienteId(expediente.getId().longValue())
                .folioGobierno(expediente.getFolioGobierno())
                .estado(estado)
                .diasHabilesRestantes(diasHabilesRestantes)
                .fechaEnvioOficioAutoridad(expediente.getFechaEnvioOficioAutoridad())
                .fechaLimiteInforme(fechaLimiteInforme)
                .vencido(vencido)
                .build();
    }

    public RegistroInformeAutoridadResponseDTO toRegistroResponse(ExpedienteEntity expediente) {
        return RegistroInformeAutoridadResponseDTO.builder()
                .expedienteId(expediente.getId().longValue())
                .folioGobierno(expediente.getFolioGobierno())
                .numeroOficioRespuesta(expediente.getNumeroOficioRespuesta())
                .fojas(expediente.getFojasInforme())
                .fechaRecepcionInforme(expediente.getFechaRecepcionInforme())
                .rutaPdfInforme(expediente.getRutaPdfInforme())
                .estadoAlerta5Dias(expediente.getEstadoAlerta5Dias())
                .build();
    }
}

