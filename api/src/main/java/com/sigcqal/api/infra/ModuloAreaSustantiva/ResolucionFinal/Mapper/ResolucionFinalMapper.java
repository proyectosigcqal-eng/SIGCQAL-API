package com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Model.ResolucionFinal;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Entity.ResolucionFinalEntity;
import com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Dto.ResolucionFinalResponseDTO;

@Component
public class ResolucionFinalMapper {

    public ResolucionFinalEntity toEntity(ResolucionFinal domain) {
        if (domain == null) {
            return null;
        }
        return ResolucionFinalEntity.builder()
                .idResolucionFinal(domain.getIdResolucionFinal())
                .fechaEmisionResolucion(domain.getFechaEmisionResolucion())
                .conceptoCobro(domain.getConceptoCobro())
                .contactoVia(domain.getContactoVia())
                .numeroCredito(domain.getNumeroCredito())
                .folioCredito(domain.getFolioCredito())
                .idExpediente(domain.getIdExpediente())
                .idAri(domain.getIdAri())
                .idQuejaRespuestaAutoridad(domain.getIdQuejaRespuestaAutoridad())
                .idEstatusQueja(domain.getIdEstatusQueja())
                .rutaResolucionFinal(domain.getRutaResolucionFinal())
                .fechaEmision(domain.getFechaEmision())
                .idEstatusExpediente(domain.getIdEstatusExpediente())
                .build();
    }

    public ResolucionFinal toDomain(ResolucionFinalEntity entity) {
        if (entity == null) {
            return null;
        }
        return ResolucionFinal.builder()
                .idResolucionFinal(entity.getIdResolucionFinal())
                .fechaEmisionResolucion(entity.getFechaEmisionResolucion())
                .conceptoCobro(entity.getConceptoCobro())
                .contactoVia(entity.getContactoVia())
                .numeroCredito(entity.getNumeroCredito())
                .folioCredito(entity.getFolioCredito())
                .idExpediente(entity.getIdExpediente())
                .idAri(entity.getIdAri())
                .idQuejaRespuestaAutoridad(entity.getIdQuejaRespuestaAutoridad())
                .idEstatusQueja(entity.getIdEstatusQueja())
                .rutaResolucionFinal(entity.getRutaResolucionFinal())
                .fechaEmision(entity.getFechaEmision())
                .idEstatusExpediente(entity.getIdEstatusExpediente())
                .build();
    }

    public ResolucionFinalResponseDTO toResponse(ResolucionFinal domain) {
        if (domain == null) {
            return null;
        }
        return ResolucionFinalResponseDTO.builder()
                .idResolucionFinal(domain.getIdResolucionFinal())
                .fechaEmisionResolucion(domain.getFechaEmisionResolucion())
                .conceptoCobro(domain.getConceptoCobro())
                .contactoVia(domain.getContactoVia())
                .numeroCredito(domain.getNumeroCredito())
                .folioCredito(domain.getFolioCredito())
                .idExpediente(domain.getIdExpediente())
                .idAri(domain.getIdAri())
                .idQuejaRespuestaAutoridad(domain.getIdQuejaRespuestaAutoridad())
                .idEstatusQueja(domain.getIdEstatusQueja())
                .rutaResolucionFinal(domain.getRutaResolucionFinal())
                .fechaEmision(domain.getFechaEmision())
                .idEstatusExpediente(domain.getIdEstatusExpediente())
                .build();
    }
}