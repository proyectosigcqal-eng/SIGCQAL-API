package com.sigcqal.api.infra.ModuloCorrespondencia.BitacoraHistorica.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Estatus.EstatusEntity;
import com.sigcqal.api.domain.ModuloCorrespondencia.BitacoraHistorica.Model.BitacoraHistorica;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.BitacoraHistorica.Entity.BitacoraHistoricaEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Entity.CorrespondenciaEntity;
import com.sigcqal.api.web.ModuloCorrespondencia.BitacoraHistorica.Dto.BitacoraHistoricaResponseDTO;

 @Component
public class BitacoraHistoricaMapper {

   public BitacoraHistorica toDomain(BitacoraHistoricaEntity entity) {
    if (entity == null) return null;

    BitacoraHistorica domain = new BitacoraHistorica();


    domain.setIdLog(entity.getIdLog());
    domain.setObservaciones(entity.getObservaciones());
    domain.setFechaMovimiento(entity.getFechaMovimiento());

  
    if (entity.getCorrespondencia() != null) {
        domain.setIdCorrespondencia(entity.getCorrespondencia().getId());
        domain.setDependenciaRemitente(entity.getCorrespondencia().getDependenciaRemitente());
        domain.setAsuntoCorrespondencia(entity.getCorrespondencia().getAsunto());
    }

    if (entity.getUsuarioAccion() != null) {
        domain.setIdUsuarioAccion(entity.getUsuarioAccion().getId());
        domain.setNombreUsuario(entity.getUsuarioAccion().getUsuarioLogin()); 
    }

    if (entity.getEstatusAnterior() != null) {
        domain.setEstatusAnterior(entity.getEstatusAnterior().getIdEstatus());
        domain.setNombreEstatusAnterior(entity.getEstatusAnterior().getNombreEstatus());
    }

    if (entity.getEstatusNuevo() != null) {
        domain.setEstatusNuevo(entity.getEstatusNuevo().getIdEstatus());
        domain.setNombreEstatusNuevo(entity.getEstatusNuevo().getNombreEstatus());
    }

    return domain;
}

    public BitacoraHistoricaEntity toEntity(BitacoraHistorica domain) {
    if (domain == null) return null;

    BitacoraHistoricaEntity entity = new BitacoraHistoricaEntity();

    if (domain.getIdLog() != null) {
        entity.setIdLog(domain.getIdLog());
    }
    
    entity.setObservaciones(domain.getObservaciones());
    entity.setFechaMovimiento(domain.getFechaMovimiento());

    if (domain.getIdCorrespondencia() != null) {
        CorrespondenciaEntity correspondencia = new CorrespondenciaEntity();
        correspondencia.setId(domain.getIdCorrespondencia());
        entity.setCorrespondencia(correspondencia);
    }

    if (domain.getIdUsuarioAccion() != null) {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(domain.getIdUsuarioAccion());
        entity.setUsuarioAccion(usuario);
    }

    if (domain.getEstatusAnterior() != null) {
        EstatusEntity estatusAnt = new EstatusEntity();
        estatusAnt.setIdEstatus(domain.getEstatusAnterior());
        entity.setEstatusAnterior(estatusAnt);
    }

    if (domain.getEstatusNuevo() != null) {
        EstatusEntity estatusNvo = new EstatusEntity();
        estatusNvo.setIdEstatus(domain.getEstatusNuevo());
        entity.setEstatusNuevo(estatusNvo);
    }

    return entity;
}

public BitacoraHistoricaResponseDTO toResponse(BitacoraHistorica domain) {
    if (domain == null) return null;

    BitacoraHistoricaResponseDTO dto = new BitacoraHistoricaResponseDTO();

    dto.setIdLog(domain.getIdLog());
    dto.setIdCorrespondencia(domain.getIdCorrespondencia());
    dto.setIdUsuarioAccion(domain.getIdUsuarioAccion());
    
    dto.setEstatusAnterior(domain.getEstatusAnterior());
    dto.setEstatusNuevo(domain.getEstatusNuevo());

    dto.setNombreUsuario(domain.getNombreUsuario());
    dto.setNombreEstatusAnterior(domain.getNombreEstatusAnterior());
    dto.setNombreEstatusNuevo(domain.getNombreEstatusNuevo());
    
    dto.setAsuntoCorrespondencia(domain.getAsuntoCorrespondencia());
    dto.setDependenciaRemitente(domain.getDependenciaRemitente());

    dto.setObservaciones(domain.getObservaciones());
    dto.setFechaMovimiento(domain.getFechaMovimiento()); 

    return dto;
}
}
