package com.sigcqal.api.infra.ModuloCorrespondencia.ReasignacionCorrespondencia.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.ReasignacionCorrespondencia.Model.ReasignacionCorrespondencia;
import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseReciboInterno.Entity.AcuseReciboInternoEntity;
import com.sigcqal.api.web.ModuloCorrespondencia.ReasignacionCorrespondencia.Dto.ReasignacionCorrespondenciaResponseDTO;

@Component
public class ReasignacionCorrespondenciaMapper {

    public ReasignacionCorrespondencia toDomain(AcuseReciboInternoEntity entity) {
        if (entity == null) return null;

        ReasignacionCorrespondencia domain = new ReasignacionCorrespondencia();

        domain.setIdAcuse(entity.getIdAcuse());
        domain.setEsDelArea(entity.getEsDelArea());

        domain.setFechaAceptacion(entity.getFechaAceptacion() != null ? entity.getFechaAceptacion().toString() : null);
        domain.setHoraAceptacion(entity.getHoraAceptacion() != null ? entity.getHoraAceptacion().toString() : null);
        domain.setIdUsuarioRevisor(entity.getUsuarioRevisor() != null ? entity.getUsuarioRevisor().getId() : null);

        if (entity.getMemorandum() != null) {
            var memo = entity.getMemorandum();
            domain.setIdMemorandum(memo.getId());
            domain.setNumMemo(memo.getNumMemo());
            domain.setFolioUnico(memo.getFolioUnico());
            domain.setObservaciones(memo.getObservaciones());
            domain.setUrlMemorandumGenerado(memo.getUrlMemorandumGenerado());

            if (memo.getFechaEmision() != null) {
                domain.setFechaEmision(memo.getFechaEmision().toString());
            }

            if (memo.getPlantilla() != null) {
                domain.setIdPlantilla(memo.getPlantilla().getId());
            }

            if (memo.getUsuarioEmisor() != null) {
                domain.setIdUsuarioEmisor(memo.getUsuarioEmisor().getId());
            }

            if (memo.getCorrespondencia() != null) {
                domain.setIdCorrespondencia(memo.getCorrespondencia().getId());
            }

            if (memo.getArea() != null) {
                domain.setIdArea(memo.getArea().getId());
            }

            if (memo.getUsuarioFirmante() != null) {
                domain.setIdUsuarioFirmante(memo.getUsuarioFirmante().getId());
            }
        }

        return domain;
    }

    public ReasignacionCorrespondenciaResponseDTO toResponse(ReasignacionCorrespondencia d) {
        ReasignacionCorrespondenciaResponseDTO dto = new ReasignacionCorrespondenciaResponseDTO();

        dto.setIdAcuse(d.getIdAcuse());
        dto.setEsDelArea(d.getEsDelArea());
        dto.setFechaAceptacion(d.getFechaAceptacion());
        dto.setHoraAceptacion(d.getHoraAceptacion());
        dto.setIdUsuarioRevisor(d.getIdUsuarioRevisor());

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

