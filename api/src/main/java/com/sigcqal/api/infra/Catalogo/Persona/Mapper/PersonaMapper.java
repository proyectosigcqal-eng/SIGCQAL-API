package com.sigcqal.api.infra.Catalogo.Persona.Mapper;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.Catalogo.Persona.Model.Persona;
import com.sigcqal.api.infra.Catalogo.Direccion.Entity.DireccionEntity;
import com.sigcqal.api.infra.Catalogo.Persona.Entity.PersonaEntity;
import com.sigcqal.api.infra.Catalogo.TipoPersona.Entity.TipoPersonaEntity;
import com.sigcqal.api.web.Catalogo.Persona.Dto.PersonaDTO;

@Component
public class PersonaMapper {

    public Persona toDomain(PersonaEntity entity) {
        if (entity == null) return null;

        Persona domain = new Persona();
        // Mapear el id desde la entidad
        domain.setId(entity.getId());
        domain.setNombre(entity.getNombre());
        domain.setApellidoPaterno(entity.getApellidoPaterno());
        domain.setApellidoMaterno(entity.getApellidoMaterno());
        domain.setCurp(entity.getCurp());
        domain.setTelefono(entity.getTelefono());
        domain.setComunidad(entity.getComunidad());
        domain.setRfc(entity.getRfc());
        domain.setRec(entity.getRec());
        domain.setIdentificacionOficial(entity.getIdentificacionOficial());
        domain.setTelefonoFijo(entity.getTelefonoFijo());
        domain.setNumeroIdFolio(entity.getNumeroIdFolio());
        domain.setCorreo(entity.getCorreo());
        domain.setTipoIdentificacion(entity.getTipoIdentificacion());


        if (entity.getDireccion() != null) {
            domain.setIdDireccion(entity.getDireccion().getId());
        }

        // Mapeo del objeto relacionado TipoPersona
        if (entity.getTipoPersona() != null) {
            domain.setIdTipoPersona(entity.getTipoPersona().getId());
            // Asumiendo que TipoPersonaEntity tiene un método getNombre()
            domain.setNomreTipoPersona(entity.getTipoPersona().getNombre()); 
        }

        return domain;
    }

    public PersonaEntity toEntity(Persona domain) {
        if (domain == null) return null;

        PersonaEntity entity = new PersonaEntity();
        // Mantener el id para actualizaciones
        entity.setId(domain.getId());

        entity.setNombre(domain.getNombre());
        entity.setApellidoPaterno(domain.getApellidoPaterno());
        entity.setApellidoMaterno(domain.getApellidoMaterno());
        entity.setCurp(domain.getCurp());
        entity.setTelefono(domain.getTelefono());
        entity.setComunidad(domain.getComunidad());
        entity.setRfc(domain.getRfc());
        entity.setRec(domain.getRec());
        entity.setIdentificacionOficial(domain.getIdentificacionOficial());
        entity.setTelefonoFijo(domain.getTelefonoFijo());
        entity.setNumeroIdFolio(domain.getNumeroIdFolio());
        entity.setCorreo(domain.getCorreo());
        entity.setTipoIdentificacion(domain.getTipoIdentificacion());

        // Instanciación y asignación del objeto DireccionEntity
        if (domain.getIdDireccion() != null) {
            // Reemplaza "DireccionEntity" con el nombre real de tu clase Entity
            DireccionEntity direccion = new DireccionEntity();
            direccion.setId(domain.getIdDireccion());
            entity.setDireccion(direccion);
        }

        // Instanciación y asignación del objeto TipoPersonaEntity
        if (domain.getIdTipoPersona() != null) {
            // Reemplaza "TipoPersonaEntity" con el nombre real de tu clase Entity
            TipoPersonaEntity tipoPersona = new TipoPersonaEntity();
            tipoPersona.setId(domain.getIdTipoPersona());
            tipoPersona.setNombre(domain.getNomreTipoPersona()); 
            entity.setTipoPersona(tipoPersona);
        }

        return entity;
    }

    public PersonaDTO toResponse(Persona domain) {
        if (domain == null) return null;

        PersonaDTO dto = new PersonaDTO();
        // Mapear id a la respuesta
        dto.setId(domain.getId());
        
       
        dto.setIdDireccion(domain.getIdDireccion());
        dto.setNombre(domain.getNombre());
        dto.setApellidoPaterno(domain.getApellidoPaterno());
        dto.setApellidoMaterno(domain.getApellidoMaterno());
        dto.setCurp(domain.getCurp());
        dto.setTelefono(domain.getTelefono());
        dto.setComunidad(domain.getComunidad());
        dto.setRfc(domain.getRfc());
        dto.setRec(domain.getRec());
        dto.setIdentificacionOficial(domain.getIdentificacionOficial());
        dto.setTelefonoFijo(domain.getTelefonoFijo());
        dto.setNumeroIdFolio(domain.getNumeroIdFolio());
        dto.setCorreo(domain.getCorreo());
        dto.setIdTipoPersona(domain.getIdTipoPersona());
        dto.setNomreTipoPersona(domain.getNomreTipoPersona());
        dto.setTipoIdentificacion(domain.getTipoIdentificacion());

        return dto;
    }
}