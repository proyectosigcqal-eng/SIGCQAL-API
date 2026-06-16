package com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Mapper;

import org.springframework.stereotype.Component;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Queja.Model.Queja;
import com.sigcqal.api.infra.Catalogo.Persona.Entity.PersonaEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Entity.QuejaEntity;
import com.sigcqal.api.web.ModuloAreaSustantiva.Queja.DTO.QuejaResponseDTO;

@Component
public class QuejaMapper {

    public Queja toDomain(QuejaEntity entity) {
        if (entity == null) return null;

        Queja domain = new Queja();
        domain.setIdQueja(entity.getIdQueja());
        domain.setRequisitoIdentificacion(entity.getRequisitoIdentificacion());
        domain.setRequisitoActosFiscales(entity.getRequisitoActosFiscales());
        domain.setRequisitoNarrativaClara(entity.getRequisitoNarrativaClara());
        domain.setRequisitoCompetenciaCedecon(entity.getRequisitoCompetenciaCedecon());
        domain.setFechaRegistro(entity.getFechaRegistro());
        domain.setUltimaActualizacion(entity.getUltimaActualizacion());

        // 1. MAPEO DESDE EXPEDIENTE
        if (entity.getExpediente() != null) {
            domain.setIdExpediente(entity.getExpediente().getId());
            domain.setFolioGobierno(entity.getExpediente().getFolioGobierno());
            domain.setFechaSolicitud(entity.getExpediente().getFechaSolicitud()); // <- NUEVO: Extracción directa de la entidad
            
            // Representante Legal
            if (entity.getExpediente().getRepresentanteLegal() != null) {
                domain.setNombreRepresentante(formarNombreCompleto(entity.getExpediente().getRepresentanteLegal()));
            }
            
            // Contribuyente -> Persona
            if (entity.getExpediente().getContribuyente() != null && 
                entity.getExpediente().getContribuyente().getPersona() != null) {
                
                PersonaEntity personaCont = entity.getExpediente().getContribuyente().getPersona();
                domain.setNombreContribuyente(formarNombreCompleto(personaCont));
                domain.setIdentificacionContribuyente(personaCont.getIdentificacionOficial());
            }
        }

        // 2. MAPEO DESDE ASESOR
        if (entity.getAsesor() != null) {
            domain.setIdAsesor(entity.getAsesor().getIdAsesor());
            if (entity.getAsesor().getPersona() != null) {
                PersonaEntity personaAsesor = entity.getAsesor().getPersona();
                domain.setNombreAsesor(formarNombreCompleto(personaAsesor));
                domain.setRfcAsesor(personaAsesor.getRfc());
            }
        }

        if (entity.getAutoridad() != null) {
            domain.setIdAutoridad(entity.getAutoridad().getId());
        }
        if (entity.getEstatusQueja() != null) {
            domain.setIdEstatusQueja(entity.getEstatusQueja());
        }

        return domain;
    }

    public QuejaResponseDTO toResponse(Queja domain) {
        if (domain == null) return null;

        QuejaResponseDTO dto = new QuejaResponseDTO();
        dto.setIdQueja(domain.getIdQueja());
        dto.setIdExpediente(domain.getIdExpediente());
        dto.setIdAsesor(domain.getIdAsesor());
        dto.setIdAutoridad(domain.getIdAutoridad());
        dto.setIdEstatusQueja(domain.getIdEstatusQueja());
        dto.setRequisitoIdentificacion(domain.getRequisitoIdentificacion());
        dto.setRequisitoActosFiscales(domain.getRequisitoActosFiscales());
        dto.setRequisitoNarrativaClara(domain.getRequisitoNarrativaClara());
        dto.setRequisitoCompetenciaCedecon(domain.getRequisitoCompetenciaCedecon());
        dto.setFechaRegistro(domain.getFechaRegistro());
        dto.setUltimaActualizacion(domain.getUltimaActualizacion());
        
        // Mapeo hacia el DTO de respuesta final
        dto.setFolioGobierno(domain.getFolioGobierno());
        dto.setFechaSolicitud(domain.getFechaSolicitud()); // <- NUEVO: Traspaso al DTO
        dto.setNombreAsesor(domain.getNombreAsesor());
        dto.setRfcAsesor(domain.getRfcAsesor());
        dto.setNombreRepresentante(domain.getNombreRepresentante());
        dto.setNombreContribuyente(domain.getNombreContribuyente());
        dto.setIdentificacionContribuyente(domain.getIdentificacionContribuyente());

        return dto;
    }

    private String formarNombreCompleto(PersonaEntity persona) {
        if (persona == null) return "";
        StringBuilder sb = new StringBuilder();
        if (persona.getNombre() != null) sb.append(persona.getNombre().trim());
        if (persona.getApellidoPaterno() != null) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(persona.getApellidoPaterno().trim());
        }
        if (persona.getApellidoMaterno() != null) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(persona.getApellidoMaterno().trim());
        }
        return sb.toString().trim();
    }
}