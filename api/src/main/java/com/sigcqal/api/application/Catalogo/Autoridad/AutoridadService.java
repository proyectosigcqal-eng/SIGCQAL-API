package com.sigcqal.api.application.Catalogo.Autoridad;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.Catalogo.Autoridad.Model.Autoridad;
import com.sigcqal.api.domain.Catalogo.Autoridad.Port.AutoridadRepositoryPort;
import com.sigcqal.api.web.Catalogo.Autoridad.Dto.AutoridadDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutoridadService {
    private final AutoridadRepositoryPort repositoryPort;

    @Cacheable(cacheNames = "autoridadesAll", key = "'all'")
    public List<AutoridadDTO> obtenerAutoridades() {
        return repositoryPort.findAll().stream().map(this::mapToResponse).toList();
    }

    @Cacheable(cacheNames = "autoridadesById", key = "#id")
    public AutoridadDTO obtenerAutoridad(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException("El id debe ser mayor a 0");
        }

        Autoridad aut = repositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("Autoridad", id));
        return mapToResponse(aut);
    }

    private AutoridadDTO mapToResponse(Autoridad dom) {
        AutoridadDTO dto = new AutoridadDTO();
        dto.setId(dom.getId());
        dto.setNombre(dom.getNombre());
        return dto;
    }
}
