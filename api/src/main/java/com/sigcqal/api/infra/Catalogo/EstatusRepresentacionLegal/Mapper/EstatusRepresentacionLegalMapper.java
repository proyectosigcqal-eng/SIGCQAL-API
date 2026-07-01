package com.sigcqal.api.infra.Catalogo.EstatusRepresentacionLegal.Mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.EstatusRepresentacionLegal.Model.EstatusRepresentacionLegal;
import com.sigcqal.api.infra.Catalogo.EstatusRepresentacionLegal.Entity.EstatusRepresentacionLegalEntity;

@Component
public class EstatusRepresentacionLegalMapper {

    public EstatusRepresentacionLegal toDomain(EstatusRepresentacionLegalEntity entity) {
        if (entity == null) {
            return null;
        }
        return new EstatusRepresentacionLegal(
                entity.getId(),
                entity.getNombreEstatus());
    }

    public List<EstatusRepresentacionLegal> toDomainList(List<EstatusRepresentacionLegalEntity> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
}
