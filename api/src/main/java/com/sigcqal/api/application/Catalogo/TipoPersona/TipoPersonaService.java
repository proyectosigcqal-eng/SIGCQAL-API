package com.sigcqal.api.application.Catalogo.TipoPersona;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.Catalogo.TipoPersona.Model.TipoPersona;
import com.sigcqal.api.domain.Catalogo.TipoPersona.Port.TipoPersonaRepositoryPort;
import com.sigcqal.api.web.Catalogo.TipoPersona.Dto.TipoPersonaDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoPersonaService {
    private final TipoPersonaRepositoryPort repositoryPort;

    @Cacheable(cacheNames = "tiposPersonasAll", key = "'all'")
    public List<TipoPersonaDTO> obtenerTiposPersonas() {
        return repositoryPort.findAll().stream().map(this::mapToResponse).toList();
    }

    @Cacheable(cacheNames = "tiposPersonasById", key = "#id")
    public TipoPersonaDTO obtenerTipoPersona(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException("El id debe ser mayor a 0");
        }

        TipoPersona tipoPersona = repositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoPersona", id));
        return mapToResponse(tipoPersona);
    }

    private TipoPersonaDTO mapToResponse(TipoPersona dom) {
        TipoPersonaDTO dto = new TipoPersonaDTO();
        dto.setId(dom.getId());
        dto.setNombre(dom.getNombre());
        return dto;
    }
}
