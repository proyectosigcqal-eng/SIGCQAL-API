package com.sigcqal.api.application.Catalogo.TipoTramite;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.Catalogo.TipoTramite.Model.TipoTramite;
import com.sigcqal.api.domain.Catalogo.TipoTramite.Port.TipoTramiteRepositoryPort;
import com.sigcqal.api.web.Catalogo.TipoTramite.Dto.TipoTramiteDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoTramiteService {
    private final TipoTramiteRepositoryPort repositoryPort;

    @Cacheable(cacheNames = "tipoTramiteAll", key = "'all'")
    public List<TipoTramiteDTO> obtenerTipoTramites() {
        return repositoryPort.findAll().stream().map(this::mapToResponse).toList();
    }

    @Cacheable(cacheNames = "tipoTramiteById", key = "#id")
    public TipoTramiteDTO obtenerTipoTramite(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException("El id debe ser mayor a 0");
        }

        TipoTramite item = repositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("TipoTramite", id));
        return mapToResponse(item);
    }

    private TipoTramiteDTO mapToResponse(TipoTramite dom) {
        TipoTramiteDTO dto = new TipoTramiteDTO();
        dto.setId(dom.getId());
        dto.setNombre(dom.getNombre());
        return dto;
    }
}
