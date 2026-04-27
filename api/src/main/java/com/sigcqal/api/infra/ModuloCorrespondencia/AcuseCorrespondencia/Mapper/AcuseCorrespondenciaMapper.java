package com.sigcqal.api.infra.ModuloCorrespondencia.AcuseCorrespondencia.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseCorrespondencia.Model.AcuseCorrespondencia;
import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseCorrespondencia.Entity.AcuseCorrespondenciaEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Entity.CorrespondenciaEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import com.sigcqal.api.web.ModuloCorrespondencia.AcuseCorrespondencia.Dto.AcuseCorrespondenciaResponseDTO;

@Component
public class AcuseCorrespondenciaMapper {

    public AcuseCorrespondencia toDomain(AcuseCorrespondenciaEntity entity) {
        if (entity == null) return null;

        AcuseCorrespondencia d = new AcuseCorrespondencia();

        d.setIdAcuseCorrespondencia(entity.getIdAcuseCorrespondencia());
        d.setFechaAceptacion(entity.getFechaAceptacion());
        d.setHoraAceptacion(entity.getHoraAceptacion());
        d.setEsDelArea(entity.getEsDelArea());

        if (entity.getUsuarioRevisor() != null) {
            d.setIdUsuarioRevisor(entity.getUsuarioRevisor().getId());
        }

        if (entity.getCorrespondencia() != null) {
            var c = entity.getCorrespondencia();

            d.setIdCorrespondencia(c.getId());
            d.setFolioUnico(c.getFolioUnico());
            d.setNumeroOficio(c.getNumeroOficio());
            d.setDependenciaRemitente(c.getDependenciaRemitente());
            d.setTitularDependencia(c.getTitularDependencia());
            d.setAsunto(c.getAsunto());

            if (c.getFechaExpedicion() != null)
                d.setFechaExpedicion(c.getFechaExpedicion().toString());

            if (c.getFechaRecibido() != null)
                d.setFechaRecibido(c.getFechaRecibido().toString());

            d.setObservaciones(c.getObservaciones());

            if (c.getArea() != null)
                d.setIdArea(c.getArea().getId());
        }

        return d;
    }

    public AcuseCorrespondenciaEntity toEntity(AcuseCorrespondencia d) {
        AcuseCorrespondenciaEntity e = new AcuseCorrespondenciaEntity();

        e.setIdAcuseCorrespondencia(d.getIdAcuseCorrespondencia());
        e.setFechaAceptacion(d.getFechaAceptacion());
        e.setHoraAceptacion(d.getHoraAceptacion());
        e.setEsDelArea(d.getEsDelArea());

        if (d.getIdCorrespondencia() != null) {
            CorrespondenciaEntity c = new CorrespondenciaEntity();
            c.setId(d.getIdCorrespondencia());
            e.setCorrespondencia(c);
        }

        if (d.getIdUsuarioRevisor() != null) {
            UsuarioEntity u = new UsuarioEntity();
            u.setId(d.getIdUsuarioRevisor());
            e.setUsuarioRevisor(u);
        }

        return e;
    }

    public AcuseCorrespondenciaResponseDTO toResponse(AcuseCorrespondencia d) {
        AcuseCorrespondenciaResponseDTO dto = new AcuseCorrespondenciaResponseDTO();

        dto.setIdAcuseCorrespondencia(d.getIdAcuseCorrespondencia());
        dto.setIdCorrespondencia(d.getIdCorrespondencia());
        dto.setIdUsuarioRevisor(d.getIdUsuarioRevisor());
        dto.setEsDelArea(d.getEsDelArea());

        dto.setFechaAceptacion(
            d.getFechaAceptacion() != null ? d.getFechaAceptacion().toString() : null
        );

        dto.setHoraAceptacion(
            d.getHoraAceptacion() != null ? d.getHoraAceptacion().toString() : null
        );

        dto.setFolioUnico(d.getFolioUnico());
        dto.setNumeroOficio(d.getNumeroOficio());
        dto.setDependenciaRemitente(d.getDependenciaRemitente());
        dto.setTitularDependencia(d.getTitularDependencia());
        dto.setAsunto(d.getAsunto());
        dto.setFechaExpedicion(d.getFechaExpedicion());
        dto.setFechaRecibido(d.getFechaRecibido());
        dto.setObservaciones(d.getObservaciones());
        dto.setIdArea(d.getIdArea());

        return dto;
    }
}