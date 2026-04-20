package com.sigcqal.api.infra.ModuloCorrespondencia.AcuseReciboInterno.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseReciboInterno.Model.AcuseReciboInterno;
import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseReciboInterno.Entity.AcuseReciboInternoEntity;

import com.sigcqal.api.infra.ModuloCorrespondencia.Memorandum.Entity.MemorandumEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;

import com.sigcqal.api.web.ModuloCorrespondencia.AcuseReciboInterno.Dto.AcuseReciboInternoResponseDTO;

@Component
public class AcuseReciboInternoMapper {

    public AcuseReciboInterno toDomain(AcuseReciboInternoEntity entity) {
    if (entity == null) return null;
    AcuseReciboInterno domain = new AcuseReciboInterno();
    
    domain.setIdAcuse(entity.getIdAcuse());
    domain.setEsDelArea(entity.getEsDelArea());
    domain.setFechaAceptacion(entity.getFechaAceptacion());

    // 1. EXTRAER EL MEMO
    if (entity.getMemorandum() != null) {
        var memo = entity.getMemorandum();
        domain.setIdMemorandum(memo.getId()); 
        
        // 🔥 VALIDAMOS NULOS PARA EVITAR EL ERROR 500 AL CREAR 🔥
        if (memo.getCorrespondencia() != null) {
            domain.setIdCorrespondencia(memo.getCorrespondencia().getId());
        }
        
        domain.setNumMemo(memo.getNumMemo());
        domain.setFolioUnico(memo.getFolioUnico());
        
        if (memo.getFechaEmision() != null) {
            domain.setFechaEmision(memo.getFechaEmision().toString());
        }
        
        if (memo.getUsuarioEmisor() != null) {
            domain.setIdUsuarioEmisor(memo.getUsuarioEmisor().getId());
        }
        
        domain.setObservaciones(memo.getObservaciones());
        domain.setUrlMemorandumGenerado(memo.getUrlMemorandumGenerado());
    }
    return domain;
}

    public AcuseReciboInternoEntity toEntity(AcuseReciboInterno domain) {
        AcuseReciboInternoEntity entity = new AcuseReciboInternoEntity();

        entity.setIdAcuse(domain.getIdAcuse());
        entity.setEsDelArea(domain.getEsDelArea());
        entity.setFechaAceptacion(domain.getFechaAceptacion());
        entity.setHoraAceptacion(domain.getHoraAceptacion());

        if (domain.getIdMemorandum() != null) {
            MemorandumEntity memorandum = new MemorandumEntity();
            memorandum.setId(domain.getIdMemorandum());
            entity.setMemorandum(memorandum);
        }
        
        if (domain.getIdUsuarioRevisor() != null) {
            UsuarioEntity usuario = new UsuarioEntity();
            usuario.setId(domain.getIdUsuarioRevisor());
            entity.setUsuarioRevisor(usuario);
        }

        return entity;
    }


    public AcuseReciboInternoResponseDTO toResponse(AcuseReciboInterno d) {

        AcuseReciboInternoResponseDTO dto = new AcuseReciboInternoResponseDTO();

        dto.setIdAcuse(d.getIdAcuse());
        dto.setEsDelArea(d.getEsDelArea());

        dto.setFechaAceptacion(
            d.getFechaAceptacion() != null ? d.getFechaAceptacion().toString() : null
        );

        dto.setHoraAceptacion(
            d.getHoraAceptacion() != null ? d.getHoraAceptacion().toString() : null
        );

        dto.setIdMemorandum(d.getIdMemorandum());
        dto.setIdCorrespondencia(d.getIdCorrespondencia());
        dto.setNumMemo(d.getNumMemo());
        dto.setFechaEmision(d.getFechaEmision());
        dto.setIdUsuarioEmisor(d.getIdUsuarioEmisor());
        dto.setFolioUnico(d.getFolioUnico());
        dto.setObservaciones(d.getObservaciones());
        dto.setUrlMemorandumGenerado(d.getUrlMemorandumGenerado());
        dto.setIdPlantilla(d.getIdPlantilla());
        dto.setIdArea(d.getIdArea());
        dto.setIdUsuarioFirmante(d.getIdUsuarioFirmante());

        return dto;
    }
}