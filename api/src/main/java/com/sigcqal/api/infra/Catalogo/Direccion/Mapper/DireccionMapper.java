package com.sigcqal.api.infra.Catalogo.Direccion.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Direccion.Model.Direccion;
import com.sigcqal.api.infra.Catalogo.Direccion.Entity.DireccionEntity;
import com.sigcqal.api.web.Catalogo.Direccion.Dto.DireccionDTO;

@Component
public class DireccionMapper {

    public DireccionEntity toEntity(Direccion domain) {
        if (domain == null) return null;
        DireccionEntity entity = new DireccionEntity();
        entity.setId(domain.getId());
        entity.setCalle(domain.getCalle());
        entity.setNumExt(domain.getNumExt());
        entity.setNumInt(domain.getNumInt());
        entity.setColonia(domain.getColonia());
        entity.setCp(domain.getCp());
        entity.setIdMunicipio(domain.getIdMunicipio());
        entity.setIdEstado(domain.getIdEstado());
        return entity;
    }

    public Direccion toDomain(DireccionEntity entity) {
        if (entity == null) return null;
        return Direccion.builder()
                .id(entity.getId())
                .calle(entity.getCalle())
                .numExt(entity.getNumExt())
                .numInt(entity.getNumInt())
                .colonia(entity.getColonia())
                .cp(entity.getCp())
                .idMunicipio(entity.getIdMunicipio())
                .idEstado(entity.getIdEstado())
                .build();
    }

    public DireccionDTO toResponse(Direccion domain) {
        if (domain == null) return null;
        DireccionDTO dto = new DireccionDTO();
        dto.setId(domain.getId());
        dto.setCalle(domain.getCalle());
        dto.setNumExt(domain.getNumExt());
        dto.setNumInt(domain.getNumInt());
        dto.setColonia(domain.getColonia());
        dto.setCp(domain.getCp());
        dto.setIdMunicipio(domain.getIdMunicipio());
        dto.setIdEstado(domain.getIdEstado());
        return dto;
    }
}