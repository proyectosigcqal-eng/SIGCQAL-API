package com.sigcqal.api.application.Catalogo.Area;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.Catalogo.Area.Model.Area;
import com.sigcqal.api.domain.Catalogo.Area.Port.AreaRepositoryPort;
import com.sigcqal.api.web.Catalogo.Area.Dto.AreaDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AreaService {
    private final AreaRepositoryPort repositoryPort;

    @Cacheable(cacheNames = "areasAll", key = "'all'")
    public List<AreaDTO> obtenerAreas() {
        return repositoryPort.findAll().stream().map(this::mapToResponse).toList();
    }

    @Cacheable(cacheNames = "areasById", key = "#id")
    public AreaDTO obtenerArea(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException("El id debe ser mayor a 0");
        }

        Area area = repositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("Área", id));
        return mapToResponse(area);
    }

    private AreaDTO mapToResponse(Area dom) {
        AreaDTO dto = new AreaDTO();
        dto.setId(dom.getId());
        dto.setNombre(dom.getNombre());
        dto.setDescripcion(dom.getDescripcion());
        return dto;
    }
}
