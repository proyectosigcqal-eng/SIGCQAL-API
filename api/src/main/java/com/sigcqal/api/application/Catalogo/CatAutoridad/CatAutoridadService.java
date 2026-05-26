package com.sigcqal.api.application.Catalogo.CatAutoridad;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.Catalogo.CatAutoridad.Model.CatAutoridad;
import com.sigcqal.api.domain.Catalogo.CatAutoridad.Port.CatAutoridadRepositoryPort;
import com.sigcqal.api.web.Catalogo.CatAutoridad.Dto.CatAutoridadDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CatAutoridadService {
    private final CatAutoridadRepositoryPort repositoryPort;

    @Cacheable(cacheNames = "catAutoridadesAll", key = "'all'")
    public List<CatAutoridadDTO> obtenerCatAutoridades() {
        return repositoryPort.findAll().stream().map(this::mapToResponse).toList();
    }

    @Cacheable(cacheNames = "catAutoridadesById", key = "#id")
    public CatAutoridadDTO obtenerCatAutoridad(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException("El id debe ser mayor a 0");
        }

        CatAutoridad cat = repositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("CatAutoridad", id));
        return mapToResponse(cat);
    }

    private CatAutoridadDTO mapToResponse(CatAutoridad dom) {
        CatAutoridadDTO dto = new CatAutoridadDTO();
        dto.setId(dom.getId());
        dto.setNombre(dom.getNombre());
        return dto;
    }
}
