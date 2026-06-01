package com.sigcqal.api.infra.ClasificacionJuridica.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ClasificacionJuridica.Model.ClasificacionJuridica;
import com.sigcqal.api.infra.Catalogo.Autoridad.Entity.AutoridadEntity;
import com.sigcqal.api.infra.Catalogo.EstatusDetalleExpediente.Entity.EstatusDetalleExpedienteEntity;
import com.sigcqal.api.infra.Catalogo.TipoActoEmitido.Entity.TipoActoEmitidoEntity;
import com.sigcqal.api.infra.Catalogo.TipoEntrada.Entity.TipoEntradaEntity;
import com.sigcqal.api.infra.ClasificacionJuridica.Entity.ClasificacionJuridicaEntity;
import com.sigcqal.api.web.ClasificacionJuridica.DTO.ClasificacionJuridicaResponseDTO;

@Component

public class ClasificacionJuridicaMapper {
    public ClasificacionJuridicaEntity toEntity(ClasificacionJuridica cj) {
    ClasificacionJuridicaEntity c = new ClasificacionJuridicaEntity();
    c.setId(cj.getId());
    if (cj.getTipoActo() != null) {
        TipoActoEmitidoEntity tipoActoEmitido = new TipoActoEmitidoEntity();
        tipoActoEmitido.setId(cj.getTipoActo().longValue());
        c.setTipoActoEmitido(tipoActoEmitido);
    }
    if (cj.getIdAutoridad() != null) {
        AutoridadEntity autoridad = new AutoridadEntity();
        autoridad.setId(cj.getIdAutoridad().longValue());
        c.setAutoridad(autoridad);
    }
    if (cj.getIdEstatusDetalleExpediente() != null) {
        EstatusDetalleExpedienteEntity estatusDetalleExpediente = new EstatusDetalleExpedienteEntity();
        estatusDetalleExpediente.setId(cj.getIdEstatusDetalleExpediente().longValue());
        c.setEstatusDetalleExpediente(estatusDetalleExpediente);
    }
    if (cj.getIdTipoEntrada() != null) {
        TipoEntradaEntity tipoEntrada = new TipoEntradaEntity();
        c.setTipoEntrada(tipoEntrada);
    }
    /*if (cj.getIdExpediente() != null) {
        ExpedienteEntity expediente = new ExpedienteEntity();
        expediente.setId(cj.getIdExpediente().longValue());
        c.setExpediente(expediente);
    }*/
    c.setCalificacionActo(cj.getCalificacionActo());
    c.setProblematica(cj.getProblematica());
    c.setSeguimientoAsesoria(cj.getSeguimientoAsesoria());
    c.setMonto(cj.getMonto());
    c.setFechaNotificacion(cj.getFechaNotificacion());
    return c;
}

public ClasificacionJuridica toDomain(ClasificacionJuridicaEntity cj) {
    ClasificacionJuridica c = new ClasificacionJuridica();
    c.setId(cj.getId());
    if(cj.getTipoActoEmitido() != null){
        c.setTipoActo(cj.getTipoActoEmitido().getId().intValue());
        c.setNombreTipoActo(cj.getTipoActoEmitido().getNombre());
    }
    if (cj.getAutoridad() != null) {
        c.setIdAutoridad(cj.getAutoridad().getId().intValue());
        c.setNombreAutoridad(cj.getAutoridad().getNombre());
    }
    if (cj.getEstatusDetalleExpediente() != null) {
        c.setIdEstatusDetalleExpediente(cj.getEstatusDetalleExpediente().getId().intValue());
        c.setNombreEstatusDetalle(cj.getEstatusDetalleExpediente().getNombre());
    }
    if (cj.getTipoEntrada() != null) {
        c.setIdTipoEntrada(cj.getTipoEntrada().getId().intValue());
        c.setNombreTipoEntrada(cj.getTipoEntrada().getNombre());
    }
    //if (cj.getExpediente() != null) {
    //    c.setIdExpediente(cj.getExpediente().getId().intValue());
    //    c.setFolioGobierno(cj.getExpediente().getFolio());
    //}
    c.setCalificacionActo(cj.getCalificacionActo());
    c.setProblematica(cj.getProblematica());
    c.setSeguimientoAsesoria(cj.getSeguimientoAsesoria());
    c.setMonto(cj.getMonto());
    c.setFechaNotificacion(cj.getFechaNotificacion());
    return c;
}

public ClasificacionJuridicaResponseDTO toResponse(ClasificacionJuridica domain){
    if (domain == null) return null;
    ClasificacionJuridicaResponseDTO dto = new ClasificacionJuridicaResponseDTO();
    dto.setCalificacionActo(domain.getCalificacionActo());
    dto.setFechaNotificacion(domain.getFechaNotificacion());
    dto.setMonto(domain.getMonto());
    dto.setNombreAutoridad(domain.getNombreAutoridad());
    dto.setNombreEstatusDetalle(domain.getNombreEstatusDetalle());
    dto.setNombreTipoActo(domain.getNombreTipoActo());
    dto.setNombreTipoEntrada(domain.getNombreTipoEntrada());
    dto.setProblematica(domain.getProblematica());
    dto.setSeguimientoAsesoria(domain.getSeguimientoAsesoria());
    return dto;
}
}
