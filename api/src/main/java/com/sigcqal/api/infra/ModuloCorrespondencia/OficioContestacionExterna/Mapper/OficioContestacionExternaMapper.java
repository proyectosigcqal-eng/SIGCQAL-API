package com.sigcqal.api.infra.ModuloCorrespondencia.OficioContestacionExterna.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.OficioContestacionExterna.Model.OficioContestacionExterna;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Entity.CorrespondenciaEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.OficioContestacionExterna.Entity.OficioContestacionExternaEntity;
import com.sigcqal.api.web.ModuloCorrespondencia.OficioContestacionExterna.Dto.OficioContestacionExternaDTOs;

@Component
public class OficioContestacionExternaMapper {

    public OficioContestacionExterna toDomain(OficioContestacionExternaEntity entity) {
        if (entity == null) return null;

        OficioContestacionExterna domain = new OficioContestacionExterna();

        domain.setIdOficioContestacion(entity.getIdOficioContestacion());
        domain.setNumOficioSalida(entity.getNumOficioSalida());
        domain.setAsuntoContestacion(entity.getAsuntoContestacion());
        domain.setCuerpoOficioTexto(entity.getCuerpoOficioTexto());
        domain.setUrlPdfFinal(entity.getUrlPdfFinal());
        domain.setFechaEmision(entity.getFechaEmision());

        if (entity.getCorrespondencia() != null) {
            var corr = entity.getCorrespondencia();
            domain.setIdCorrespondencia(corr.getId());
            domain.setFolioCorrespondencia(corr.getFolioUnico());
            domain.setAsuntoCorrespondencia(corr.getAsunto());
        }

        if (entity.getUsuarioEmisor() != null) {
            domain.setIdUsuarioEmisor(entity.getUsuarioEmisor().getId());
        }

        return domain;
    }

    public OficioContestacionExternaEntity toEntity(OficioContestacionExterna domain) {
        if (domain == null) return null;

        OficioContestacionExternaEntity entity = new OficioContestacionExternaEntity();

        entity.setIdOficioContestacion(domain.getIdOficioContestacion());
        entity.setNumOficioSalida(domain.getNumOficioSalida());
        entity.setAsuntoContestacion(domain.getAsuntoContestacion());
        entity.setCuerpoOficioTexto(domain.getCuerpoOficioTexto());
        entity.setUrlPdfFinal(domain.getUrlPdfFinal());
        entity.setFechaEmision(domain.getFechaEmision());

        if (domain.getIdCorrespondencia() != null) {
            CorrespondenciaEntity corr = new CorrespondenciaEntity();
            corr.setId(domain.getIdCorrespondencia());
            entity.setCorrespondencia(corr);
        }

        if (domain.getIdUsuarioEmisor() != null) {
            UsuarioEntity usuario = new UsuarioEntity();
            usuario.setId(domain.getIdUsuarioEmisor());
            entity.setUsuarioEmisor(usuario);
        }

        return entity;
    }

    public OficioContestacionExternaDTOs.Response toResponse(OficioContestacionExterna domain) {
        if (domain == null) return null;

        OficioContestacionExternaDTOs.Response dto = new OficioContestacionExternaDTOs.Response();

        dto.setIdOficioContestacion(domain.getIdOficioContestacion());
        dto.setIdCorrespondencia(domain.getIdCorrespondencia());
        dto.setIdUsuarioEmisor(domain.getIdUsuarioEmisor());
        dto.setNumOficioSalida(domain.getNumOficioSalida());
        dto.setAsuntoContestacion(domain.getAsuntoContestacion());
        dto.setCuerpoOficioTexto(domain.getCuerpoOficioTexto());
        dto.setUrlPdfFinal(domain.getUrlPdfFinal());
        dto.setFechaEmision(domain.getFechaEmision() != null ? domain.getFechaEmision().toString() : null);
        dto.setFolioCorrespondencia(domain.getFolioCorrespondencia());
        dto.setAsuntoCorrespondencia(domain.getAsuntoCorrespondencia());

        return dto;
    }
}
