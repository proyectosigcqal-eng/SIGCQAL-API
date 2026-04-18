package com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Mapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.Correspondencia.Model.Correspondencia;
import com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Entity.CorrespondenciaEntradaEntity;

@Component
public class CorrespondenciaEntradaMapper {
    private static final Pattern FOLIO_PATTERN = Pattern.compile("^CEDECON-CORR-(\\d+)/(\\d{4})$");

    public Correspondencia toDomain(CorrespondenciaEntradaEntity entity) {
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
        dom.setIdEstatus(entity.getIdEstatus());
        dom.setIdUsuarioCaptura(entity.getIdUsuarioCaptura());
        dom.setObservaciones(entity.getObservaciones());
        return dom;
    }

    public CorrespondenciaEntradaEntity toEntity(Correspondencia domain) {
        if (domain == null) return null;

        CorrespondenciaEntradaEntity entity = new CorrespondenciaEntradaEntity();
        entity.setId(domain.getId());
        entity.setFolioUnico(domain.getFolioUnico());
        entity.setNumeroOficio(domain.getNumeroOficio());
        entity.setFechaExpedicion(domain.getFechaExpedicion());
        entity.setDependenciaRemitente(domain.getDependenciaRemitente());
        entity.setTitularDependencia(domain.getTitularDependencia());
        entity.setAsunto(domain.getAsunto());
        entity.setFechaRecibido(domain.getFechaRecibido());
        entity.setIdEstatus(domain.getIdEstatus());
        entity.setIdUsuarioCaptura(domain.getIdUsuarioCaptura());
        entity.setObservaciones(domain.getObservaciones());
        return entity;
    }

    private Long parseConsecutivo(String folioUnico) {
        if (folioUnico == null) return null;
        Matcher matcher = FOLIO_PATTERN.matcher(folioUnico);
        if (!matcher.matches()) return null;
        return Long.valueOf(matcher.group(1));
    }
}
