package com.sigcqal.api.application.Catalogo.ControlFoliosConfig;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.Catalogo.ControlFoliosConfig.Model.ControlFoliosConfig;
import com.sigcqal.api.domain.Catalogo.ControlFoliosConfig.Port.ControlFoliosConfigRepositoryPort;
import com.sigcqal.api.web.Catalogo.ControlFoliosConfig.Dto.ControlFoliosConfigDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ControlFoliosConfigService {
    private final ControlFoliosConfigRepositoryPort repositoryPort;

    @Cacheable(cacheNames = "controlFoliosConfigAll", key = "'all'")
    public List<ControlFoliosConfigDTO> obtenerControlFoliosConfigs() {
        return repositoryPort.findAll().stream().map(this::mapToResponse).toList();
    }

    @Cacheable(cacheNames = "controlFoliosConfigById", key = "#id")
    public ControlFoliosConfigDTO obtenerControlFoliosConfig(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException("El id debe ser mayor a 0");
        }

        ControlFoliosConfig item = repositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("ControlFoliosConfig", id));
        return mapToResponse(item);
    }

    private ControlFoliosConfigDTO mapToResponse(ControlFoliosConfig dom) {
        ControlFoliosConfigDTO dto = new ControlFoliosConfigDTO();
        dto.setId(dom.getId());
        dto.setNombre(dom.getNombre());
        return dto;
    }
}
