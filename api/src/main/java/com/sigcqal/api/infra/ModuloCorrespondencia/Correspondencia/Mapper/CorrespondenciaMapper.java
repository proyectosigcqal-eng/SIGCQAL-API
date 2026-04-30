package com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Mapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.Correspondencia.Model.Correspondencia;
import com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Entity.CorrespondenciaEntity;
import com.sigcqal.api.web.ModuloCorrespondencia.Correspondencia.Dto.RegistrarCorrespondenciaResponseDTO;

@Component
public class CorrespondenciaMapper {
    private static final Pattern FOLIO_PATTERN = Pattern.compile("^CEDECON-CORR-(\\d+)/(\\d{4})$");

    public Correspondencia toDomain(CorrespondenciaEntity entity) {
        if (entity == null) return null;

        Correspondencia dom = new Correspondencia();
        dom.setId(entity.getId());
        dom.setFolioUnico(entity.getFolioUnico());
        dom.setConsecutivo(parseConsecutivo(entity.getFolioUnico()));
        dom.setNumeroOficio(entity.getNumeroOficio());
        dom.setFechaExpedicion(entity.getFechaExpedicion());
        dom.setDependenciaRemitente(entity.getDependenciaRemitente());
        dom.setTitularDependencia(entity.getTitularDependencia());
        dom.setAsunto(entity.getAsunto());
        dom.setFechaRecibido(entity.getFechaRecibido());
        dom.setObservaciones(entity.getObservaciones());
        dom.setIdEstatus(entity.getIdEstatus());
        dom.setIdUsuarioCaptura(entity.getIdUsuarioCaptura());
        dom.setIdArea(entity.getIdArea());

        if (entity.getUsuarioCaptura() != null) {
            dom.setIdUsuarioCaptura(entity.getUsuarioCaptura().getId());
        }

         if (entity.getArea() != null) {
            dom.setIdArea(entity.getArea().getId());
            dom.setNombreArea(entity.getArea().getNombre());
        }

        if (entity.getEstatus() != null) {
            dom.setIdEstatus(entity.getEstatus().getId());
        }
        
        return dom;
    }

    private Long parseConsecutivo(String folioUnico) {
        if (folioUnico == null) return null;

        Matcher matcher = FOLIO_PATTERN.matcher(folioUnico);
        if (!matcher.matches()) return null;

        return Long.valueOf(matcher.group(1));
    }

    public CorrespondenciaEntity toEntity(Correspondencia domain) {
        if (domain == null) return null;

        CorrespondenciaEntity entity = new CorrespondenciaEntity();
        entity.setId(domain.getId());
        entity.setFolioUnico(domain.getFolioUnico());
        entity.setNumeroOficio(domain.getNumeroOficio());
        entity.setFechaExpedicion(domain.getFechaExpedicion());
        entity.setDependenciaRemitente(domain.getDependenciaRemitente());
        entity.setTitularDependencia(domain.getTitularDependencia());
        entity.setAsunto(domain.getAsunto());
        entity.setFechaRecibido(domain.getFechaRecibido());
        entity.setObservaciones(domain.getObservaciones());
        entity.setIdUsuarioCaptura(domain.getIdUsuarioCaptura());
        entity.setIdArea(domain.getIdArea());
        entity.setIdEstatus(domain.getIdEstatus());

        return entity;
    }

    public RegistrarCorrespondenciaResponseDTO toResponse(Correspondencia domain) {
        if (domain == null) return null;

        RegistrarCorrespondenciaResponseDTO dto = new RegistrarCorrespondenciaResponseDTO();
        dto.setId(domain.getId());
        dto.setConsecutivo(domain.getConsecutivo());
        dto.setFolioUnico(domain.getFolioUnico());
        dto.setNumeroOficio(domain.getNumeroOficio());
        dto.setFechaExpedicion(domain.getFechaExpedicion());
        dto.setDependenciaRemitente(domain.getDependenciaRemitente());
        dto.setTitularDependencia(domain.getTitularDependencia());
        dto.setAsunto(domain.getAsunto());
        dto.setFechaRecibido(domain.getFechaRecibido());
        dto.setObservaciones(domain.getObservaciones());
        dto.setIdEstatus(domain.getIdEstatus());
        dto.setIdUsuarioCaptura(domain.getIdUsuarioCaptura());
        dto.setIdArea(domain.getIdArea());
        dto.setNombreArea(domain.getNombreArea());
        return dto;
    }
}
