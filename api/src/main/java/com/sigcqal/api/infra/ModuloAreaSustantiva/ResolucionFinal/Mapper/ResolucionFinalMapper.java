package com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Model.ResolucionFinal;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ContestacionAutoridad.Entity.ContestacionAutoridadEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAri.Entity.QuejasAriEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ResolucionFinal.Entity.ResolucionFinalEntity;
import com.sigcqal.api.web.ModuloAreaSustantiva.ResolucionFinal.Dto.ResolucionFinalResponseDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
public class ResolucionFinalMapper {

    /**
     * EntityManager se usa para crear proxies ligeros (getReference) de las
     * entidades relacionadas. No hace SELECT — solo crea un proxy con el ID
     * para que Hibernate escriba correctamente la FK en el INSERT.
     */
    @PersistenceContext
    private EntityManager em;

    public ResolucionFinalEntity toEntity(ResolucionFinal domain) {
        if (domain == null) return null;

        ResolucionFinalEntity.ResolucionFinalEntityBuilder builder = ResolucionFinalEntity.builder()
                .idResolucionFinal(domain.getIdResolucionFinal())
                .fechaEmisionResolucion(domain.getFechaEmisionResolucion())
                .conceptoCobro(domain.getConceptoCobro())
                .contactoVia(domain.getContactoVia())
                .numeroCredito(domain.getNumeroCredito())
                .folioCredito(domain.getFolioCredito())
                .idEstatusQueja(domain.getIdEstatusQueja())
                .rutaResolucionFinal(domain.getRutaResolucionFinal())
                .fechaEmision(domain.getFechaEmision())
                .idEstatusExpediente(domain.getIdEstatusExpediente());

        // ★ FIX PRINCIPAL: los campos id_expediente e id_ari tienen
        //   insertable=false en @Column, así que SOLO se pueden escribir
        //   a través del @ManyToOne. Hay que setear la entidad relacionada,
        //   no el campo escalar.
        //
        //   getReference() crea un proxy sin ir a BD — es exactamente lo
        //   que necesitamos aquí: solo la FK para el INSERT.

        if (domain.getIdExpediente() != null) {
            builder.expediente(em.getReference(ExpedienteEntity.class, domain.getIdExpediente()));
        }

        if (domain.getIdAri() != null) {
            builder.quejaAri(em.getReference(QuejasAriEntity.class, domain.getIdAri()));
        }

        if (domain.getIdQuejaRespuestaAutoridad() != null) {
            builder.contestacionAutoridad(
                em.getReference(ContestacionAutoridadEntity.class,
                    domain.getIdQuejaRespuestaAutoridad().longValue()));
        }

        return builder.build();
    }

    public ResolucionFinal toDomain(ResolucionFinalEntity entity) {
        if (entity == null) return null;

        ResolucionFinal.ResolucionFinalBuilder builder = ResolucionFinal.builder()
                .idResolucionFinal(entity.getIdResolucionFinal())
                .fechaEmisionResolucion(entity.getFechaEmisionResolucion())
                .conceptoCobro(entity.getConceptoCobro())
                .contactoVia(entity.getContactoVia())
                .numeroCredito(entity.getNumeroCredito())
                .folioCredito(entity.getFolioCredito())
                .idExpediente(entity.getIdExpediente())
                .idAri(entity.getIdAri())
                .idQuejaRespuestaAutoridad(entity.getIdQuejaRespuestaAutoridad() != null
                    ? entity.getIdQuejaRespuestaAutoridad().intValue() : null)
                .idEstatusQueja(entity.getIdEstatusQueja())
                .rutaResolucionFinal(entity.getRutaResolucionFinal())
                .fechaEmision(entity.getFechaEmision())
                .idEstatusExpediente(entity.getIdEstatusExpediente());

        if (entity.getContestacionAutoridad() != null) {
            builder.numeroOficio(entity.getContestacionAutoridad().getNumeroOficio());
        }

        if (entity.getQuejaAri() != null) {
            builder.numExpedienteOficial(entity.getQuejaAri().getNumExpedienteOficial());
            builder.numeroCreditoAri(entity.getQuejaAri().getMultasCredito());
        }

        if (entity.getExpediente() != null) {
            builder.folioGobierno(entity.getExpediente().getFolioGobierno());

            if (entity.getExpediente().getContribuyente() != null
                    && entity.getExpediente().getContribuyente().getPersona() != null) {

                var p = entity.getExpediente().getContribuyente().getPersona();

                String nombreCompleto = String.join(" ",
                    p.getNombre()         != null ? p.getNombre()         : "",
                    p.getApellidoPaterno() != null ? p.getApellidoPaterno() : "",
                    p.getApellidoMaterno() != null ? p.getApellidoMaterno() : ""
                ).trim();

                builder.nombreContribuyente(nombreCompleto);
                builder.identificacionOficial(p.getIdentificacionOficial());
            }
        }

        return builder.build();
    }

    public ResolucionFinalResponseDTO toResponse(ResolucionFinal domain) {
        if (domain == null) return null;
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
                .numeroOficio(domain.getNumeroOficio())
                .folioGobierno(domain.getFolioGobierno())
                .nombreContribuyente(domain.getNombreContribuyente())
                .identificacionOficial(domain.getIdentificacionOficial())
                .numExpedienteOficial(domain.getNumExpedienteOficial())
                .numeroCreditoAri(domain.getNumeroCreditoAri())
                .build();
    }
}