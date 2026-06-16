package com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAri.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejasAri.Model.QuejasAri;
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAri.Entity.QuejasAriEntity;
import com.sigcqal.api.web.ModuloAreaSustantiva.QuejasAri.Dto.QuejasAriResponseDTO;

@Component
public class QuejasAriMapper {

    public QuejasAriEntity toEntity(QuejasAri domain) {
        if (domain == null) return null;

        QuejasAriEntity entity = new QuejasAriEntity();

        if (domain.getIdAri() != null) {
            entity.setIdAri(domain.getIdAri());
        }

        entity.setIdQueja(domain.getIdQueja());
        entity.setIdCir(domain.getIdCir());
        entity.setNumExpedienteOficial(domain.getNumExpedienteOficial());
        entity.setSintesisActosOmisiones(domain.getSintesisActosOmisiones());
        entity.setNombreEncargadoFirma(domain.getNombreEncargadoFirma());
        entity.setFechaAcuerdo(domain.getFechaAcuerdo());
        entity.setRutaPdfAri(domain.getRutaPdfAri());
        entity.setIdPlantillaQuejaAri(domain.getIdPlantillaQuejaAri());
        entity.setMultasRequerimientos(domain.getMultasRequerimientos());
        entity.setMultasCredito(domain.getMultasCredito());
        entity.setInstituto(domain.getInstituto());

        // NOTA: Se eliminaron por completo las asignaciones de campos foráneos 
        // hacia la entidad para evitar que se intenten persistir en la BD.

        return entity;
    }

    public QuejasAri toDomain(QuejasAriEntity entity) {
        if (entity == null) return null;

        QuejasAri domain = new QuejasAri();

        domain.setIdAri(entity.getIdAri());
        domain.setIdQueja(entity.getIdQueja());
        domain.setIdCir(entity.getIdCir());
        domain.setNumExpedienteOficial(entity.getNumExpedienteOficial());
        domain.setSintesisActosOmisiones(entity.getSintesisActosOmisiones());
        domain.setNombreEncargadoFirma(entity.getNombreEncargadoFirma());
        domain.setFechaAcuerdo(entity.getFechaAcuerdo());
        domain.setRutaPdfAri(entity.getRutaPdfAri());
        domain.setIdPlantillaQuejaAri(entity.getIdPlantillaQuejaAri());
        domain.setMultasRequerimientos(entity.getMultasRequerimientos());
        domain.setMultasCredito(entity.getMultasCredito());
        domain.setInstituto(entity.getInstituto());
        return domain;
    }

    public QuejasAriResponseDTO toResponse(QuejasAri domain) {
        if (domain == null) return null;

        QuejasAriResponseDTO response = new QuejasAriResponseDTO();

        response.setIdAri(domain.getIdAri());
        response.setIdQueja(domain.getIdQueja());
        response.setIdCir(domain.getIdCir());
        response.setNumExpedienteOficial(domain.getNumExpedienteOficial());
        response.setSintesisActosOmisiones(domain.getSintesisActosOmisiones());
        response.setNombreEncargadoFirma(domain.getNombreEncargadoFirma());
        response.setFechaAcuerdo(domain.getFechaAcuerdo());
        response.setRutaPdfAri(domain.getRutaPdfAri());
        response.setIdPlantillaQuejaAri(domain.getIdPlantillaQuejaAri());
        response.setMultasRequerimientos(domain.getMultasRequerimientos());
        response.setMultasCredito(domain.getMultasCredito());
        response.setInstituto(domain.getInstituto());
        // Atributo informativo transitorio (no va a la BD)
        response.setNombrePlantilla(domain.getNombrePlantilla());

        return response;
    }
}