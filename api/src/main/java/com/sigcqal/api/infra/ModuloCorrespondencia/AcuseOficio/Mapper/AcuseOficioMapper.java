package com.sigcqal.api.infra.ModuloCorrespondencia.AcuseOficio.Mapper;

import org.springframework.stereotype.Component;
import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseOficio.Model.AcuseOficio;
import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseOficio.Entity.AcuseOficioEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.Oficio.Entity.OficioEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import com.sigcqal.api.web.ModuloCorrespondencia.AcuseOficio.Dto.AcuseOficioResponseDTO;

@Component
public class AcuseOficioMapper {

    public AcuseOficio toDomain(AcuseOficioEntity entity) {
        if (entity == null) return null;
        AcuseOficio domain = new AcuseOficio();
        
        domain.setIdAcuseOficio(entity.getIdAcuseOficio());
        domain.setEsDelArea(entity.getEsDelArea());
        domain.setFechaAceptacion(entity.getFechaAceptacion());
        domain.setHoraAceptacion(entity.getHoraAceptacion());

        if (entity.getOficio() != null) {
            var oficio = entity.getOficio();
            domain.setIdOficio(oficio.getId());
            domain.setNumMemo(oficio.getNumMemo());
            domain.setInstruccionSeguimiento(oficio.getInstruccionSeguimiento());
            domain.setFolioUnico(oficio.getFolioUnico());
            domain.setObservaciones(oficio.getObservaciones());
            domain.setUrlMemorandumGenerado(oficio.getUrlMemorandumGenerado());
            
            if (oficio.getFechaEmision() != null) domain.setFechaEmision(oficio.getFechaEmision().toString());
            if (oficio.getCorrespondencia() != null) domain.setIdCorrespondencia(oficio.getCorrespondencia().getId());
            if (oficio.getPlantilla() != null) domain.setIdPlantilla(oficio.getPlantilla().getId());
            if (oficio.getUsuarioEmisor() != null) domain.setIdUsuarioEmisor(oficio.getUsuarioEmisor().getId());
            if (oficio.getUsuarioFirmante() != null) domain.setIdUsuarioFirmante(oficio.getUsuarioFirmante().getId());
            
            if (oficio.getArea() != null) {
                domain.setIdArea(oficio.getArea().getId());
                domain.setNombreArea(oficio.getArea().getNombre()); 
            }
        }
        
        if (entity.getUsuarioRevisor() != null) {
            domain.setIdUsuarioRevisor(entity.getUsuarioRevisor().getId());
        }
        
        return domain;
    }

    public AcuseOficioEntity toEntity(AcuseOficio domain) {
        if (domain == null) return null;
        AcuseOficioEntity entity = new AcuseOficioEntity();
        entity.setIdAcuseOficio(domain.getIdAcuseOficio());
        entity.setEsDelArea(domain.getEsDelArea());
        entity.setFechaAceptacion(domain.getFechaAceptacion());
        entity.setHoraAceptacion(domain.getHoraAceptacion());

        if (domain.getIdOficio() != null) {
            OficioEntity oficio = new OficioEntity();
            oficio.setId(domain.getIdOficio());
            entity.setOficio(oficio);
        }

        if (domain.getIdUsuarioRevisor() != null) {
            UsuarioEntity usuario = new UsuarioEntity();
            usuario.setId(domain.getIdUsuarioRevisor());
            entity.setUsuarioRevisor(usuario);
        }
        return entity;
    }

    public AcuseOficioResponseDTO toResponse(AcuseOficio d) {
        if (d == null) return null;
        AcuseOficioResponseDTO dto = new AcuseOficioResponseDTO();
        dto.setIdAcuseOficio(d.getIdAcuseOficio());
        dto.setEsDelArea(d.getEsDelArea());
        dto.setFechaAceptacion(d.getFechaAceptacion() != null ? d.getFechaAceptacion().toString() : null);
        dto.setHoraAceptacion(d.getHoraAceptacion() != null ? d.getHoraAceptacion().toString() : null);
        dto.setIdUsuarioRevisor(d.getIdUsuarioRevisor());
        dto.setIdOficio(d.getIdOficio());
        dto.setIdCorrespondencia(d.getIdCorrespondencia());
        dto.setNumOficio(d.getNumMemo()); 
        dto.setInstruccionSeguimiento(d.getInstruccionSeguimiento());
        dto.setFechaEmision(d.getFechaEmision());
        dto.setIdUsuarioEmisor(d.getIdUsuarioEmisor());
        dto.setFolioUnico(d.getFolioUnico());
        dto.setObservaciones(d.getObservaciones());
        dto.setUrlMemorandumGenerado(d.getUrlMemorandumGenerado());
        dto.setIdPlantilla(d.getIdPlantilla());
        dto.setIdArea(d.getIdArea());
        dto.setNombreArea(d.getNombreArea());
        dto.setIdUsuarioFirmante(d.getIdUsuarioFirmante());
        return dto;
    }
}