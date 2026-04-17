package com.sigcqal.api.infra.ModuloCorrespondencia.AcuseReciboInterno.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseReciboInterno.Model.AcuseReciboInterno;
import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseReciboInterno.Entity.AcuseReciboInternoEntity;

@Component
public class AcuseReciboInternoMapper {

    public AcuseReciboInterno toDomain(AcuseReciboInternoEntity entity) {
        if (entity == null) return null;

        AcuseReciboInterno domain = new AcuseReciboInterno();

        domain.setIdAcuse(entity.getIdAcuse());
        domain.setEsDelArea(entity.getEsDelArea());
        domain.setFechaAceptacion(entity.getFechaAceptacion());
        domain.setHoraAceptacion(entity.getHoraAceptacion());

        if (entity.getUsuarioRevisor() != null) {
            domain.setIdUsuarioRevisor(entity.getUsuarioRevisor().getId());
        }

        if (entity.getMemorandum() != null) {
            var memo = entity.getMemorandum();

            domain.setIdMemorandum(memo.getId());
            domain.setIdCorrespondencia(memo.getCorrespondencia().getId());
            domain.setNumMemo(memo.getNumMemo());
            domain.setFechaEmision(memo.getFechaEmision().toString());
            domain.setIdUsuarioEmisor(memo.getUsuarioEmisor().getId());
            domain.setFolioUnico(memo.getFolioUnico());
            domain.setObservaciones(memo.getObservaciones());
            domain.setUrlMemorandumGenerado(memo.getUrlMemorandumGenerado());
            domain.setIdPlantilla(memo.getPlantilla().getId());
            domain.setIdUsuarioFirmante(memo.getUsuarioFirmante().getId());
        }

        return domain;
    }

    public AcuseReciboInternoEntity toEntity(AcuseReciboInterno domain) {
        AcuseReciboInternoEntity entity = new AcuseReciboInternoEntity();

        entity.setIdAcuse(domain.getIdAcuse());
        entity.setEsDelArea(domain.getEsDelArea());
        entity.setFechaAceptacion(domain.getFechaAceptacion());
        entity.setHoraAceptacion(domain.getHoraAceptacion());

        return entity;
    }
}