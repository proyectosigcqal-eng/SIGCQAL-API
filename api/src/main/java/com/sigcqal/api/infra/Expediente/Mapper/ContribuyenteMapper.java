package com.sigcqal.api.infra.Expediente.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Expediente.Model.Contribuyente;
import com.sigcqal.api.infra.Expediente.Entity.ContribuyenteEntity;

@Component
public class ContribuyenteMapper {
    public ContribuyenteEntity toEntity(Contribuyente domain) {
        if (domain == null) {
            return null;
        }
        ContribuyenteEntity entity = new ContribuyenteEntity();
        entity.setId(domain.getId());
        entity.setRfc(domain.getRfc());
        entity.setRazonSocial(domain.getRazonSocial());
        entity.setIdDireccion(domain.getIdDireccion());
        entity.setCorreoElectronico(domain.getCorreoElectronico());
        entity.setTelefono(domain.getTelefono());
        return entity;
    }

    public Contribuyente toDomain(ContribuyenteEntity entity) {
        if (entity == null) {
            return null;
        }
        return Contribuyente.builder()
                .id(entity.getId())
                .rfc(entity.getRfc())
                .razonSocial(entity.getRazonSocial())
                .idDireccion(entity.getIdDireccion())
                .correoElectronico(entity.getCorreoElectronico())
                .telefono(entity.getTelefono())
                .build();
    }
}
