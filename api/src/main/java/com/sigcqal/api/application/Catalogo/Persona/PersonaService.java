package com.sigcqal.api.application.Catalogo.Persona;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.Catalogo.Persona.Model.Persona;
import com.sigcqal.api.domain.Catalogo.Persona.Port.PersonaRepositoryPort;
import com.sigcqal.api.web.Catalogo.Persona.Dto.PersonaDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonaService {
    private final PersonaRepositoryPort repositoryPort;

    @Cacheable(cacheNames = "personasAll", key = "'all'")
    public List<PersonaDTO> obtenerPersonas() {
        return repositoryPort.findAll().stream().map(this::mapToResponse).toList();
    }

    @Cacheable(cacheNames = "personasById", key = "#id")
    public PersonaDTO obtenerPersona(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException("El id debe ser mayor a 0");
        }

        Persona persona = repositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("Persona", id));
        return mapToResponse(persona);
    }

    private PersonaDTO mapToResponse(Persona dom) {
        PersonaDTO dto = new PersonaDTO();
        dto.setId(dom.getId());
        dto.setIdDireccion(dom.getIdDireccion());
        dto.setNombre(dom.getNombre());
        dto.setApellidoPaterno(dom.getApellidoPaterno());
        dto.setApellidoMaterno(dom.getApellidoMaterno());
        dto.setCurp(dom.getCurp());
        dto.setTelefono(dom.getTelefono());
        return dto;
    }
}
