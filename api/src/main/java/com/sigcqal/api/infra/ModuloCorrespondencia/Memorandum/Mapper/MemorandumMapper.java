package com.sigcqal.api.infra.ModuloCorrespondencia.Memorandum.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.ModuloCorrespondencia.Memorandum.Model.Memorandum;
import com.sigcqal.api.infra.Catalogo.Area.Entity.AreaEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Entity.CorrespondenciaEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.Memorandum.Entity.MemorandumEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.PlantillaMemorandum.Entity.PlantillaMemorandumEntity;
import com.sigcqal.api.web.ModuloCorrespondencia.Memorandum.Dto.MemorandumResponseDTO;

@Component
public class MemorandumMapper {

    public MemorandumEntity toEntity(Memorandum domain) {
        if (domain == null) return null;

        MemorandumEntity entity = new MemorandumEntity();

        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }

        entity.setFolioUnico(domain.getFolioUnico());
        entity.setInstruccionSeguimiento(domain.getInstruccionSeguimiento());
        entity.setObservaciones(domain.getObservaciones());
        entity.setUrlMemorandumGenerado(domain.getUrlSolicitudMemorandum());

        if (domain.getIdCorrespondencia() != null) {
            CorrespondenciaEntity correspondencia = new CorrespondenciaEntity();
            correspondencia.setId(domain.getIdCorrespondencia());
            entity.setCorrespondencia(correspondencia);
        }

        if (domain.getIdPlantilla() != null) {
            PlantillaMemorandumEntity plantilla = new PlantillaMemorandumEntity();
            plantilla.setId(domain.getIdPlantilla());
            entity.setPlantilla(plantilla);
        }

        if (domain.getIdUsuarioFirmante() != null) {
            UsuarioEntity firmante = new UsuarioEntity();
            firmante.setId(domain.getIdUsuarioFirmante());
            entity.setUsuarioFirmante(firmante);
        }

        if (domain.getIdUsuarioEmisor() != null) {
            UsuarioEntity emisor = new UsuarioEntity();
            emisor.setId(domain.getIdUsuarioEmisor());
            entity.setUsuarioEmisor(emisor);
        }

        if (domain.getIdArea() != null) {
            AreaEntity area = new AreaEntity();
            area.setId(domain.getIdArea());
            entity.setArea(area);
            
        }

        return entity;
    }

   public Memorandum toDomain(MemorandumEntity entity) {
        if (entity == null) return null;

        Memorandum domain = new Memorandum();

        domain.setId(entity.getId()); 
        domain.setFolioUnico(entity.getFolioUnico());
        domain.setInstruccionSeguimiento(entity.getInstruccionSeguimiento());
        domain.setObservaciones(entity.getObservaciones());
        domain.setUrlSolicitudMemorandum(entity.getUrlMemorandumGenerado());

     
        if (entity.getCorrespondencia() != null) {
            domain.setIdCorrespondencia(entity.getCorrespondencia().getId());
            domain.setAsunto(entity.getCorrespondencia().getAsunto());  
            domain.setNumeroOficio(entity.getCorrespondencia().getNumeroOficio());
        }

        if (entity.getPlantilla() != null) {
            domain.setIdPlantilla(entity.getPlantilla().getId());
            domain.setNombrePlantilla(entity.getPlantilla().getNombrePlantilla());
        }

      
        if (entity.getUsuarioEmisor() != null) {
            domain.setIdUsuarioEmisor(entity.getUsuarioEmisor().getId());
            domain.setNombreUsuarioEmisor(entity.getUsuarioEmisor().getUsuarioLogin());
        }

        if (entity.getUsuarioFirmante() != null) {
            domain.setIdUsuarioFirmante(entity.getUsuarioFirmante().getId());
            domain.setNombreUsuarioFirmante(entity.getUsuarioFirmante().getUsuarioLogin());
        }

         if (entity.getArea() != null) {
            domain.setIdArea(entity.getArea().getId());
            domain.setNombreArea(entity.getArea().getNombre());
        }

        return domain; 
    }

    public MemorandumResponseDTO toResponse(Memorandum domain) {
        if (domain == null) return null;

        MemorandumResponseDTO dto = new MemorandumResponseDTO();
        dto.setId(domain.getId());
        dto.setFolioUnico(domain.getFolioUnico());
        dto.setInstruccionSeguimiento(domain.getInstruccionSeguimiento());
        dto.setObservaciones(domain.getObservaciones());
        dto.setIdArea(domain.getIdArea());

        dto.setIdCorrespondencia(domain.getIdCorrespondencia());
        dto.setIdUsuarioEmisor(domain.getIdUsuarioEmisor());
        dto.setIdUsuarioFirmante(domain.getIdUsuarioFirmante());
        dto.setIdPlantilla(domain.getIdPlantilla());
        
        dto.setAsuntoCorrespondencia(domain.getAsunto()); 
        dto.setNombreUsuarioEmisor(domain.getNombreUsuarioEmisor());
        dto.setNombreUsuarioFirmante(domain.getNombreUsuarioFirmante());
        dto.setNombrePlantilla(domain.getNombrePlantilla());

        return dto;
    }
}