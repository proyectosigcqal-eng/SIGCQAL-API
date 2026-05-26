package com.sigcqal.api.application.Catalogo.CatTipoProceso;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.Catalogo.CatTipoProceso.Model.CatTipoProceso;
import com.sigcqal.api.domain.Catalogo.CatTipoProceso.Port.CatTipoProcesoRepositoryPort;
import com.sigcqal.api.web.Catalogo.CatTipoProceso.Dto.CatTipoProcesoDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CatTipoProcesoService {
    private final CatTipoProcesoRepositoryPort repositoryPort;

    @Cacheable(cacheNames = "catTipoProcesoAll", key = "'all'")
    public List<CatTipoProcesoDTO> obtenerCatTipoProcesos() {
        return repositoryPort.findAll().stream().map(this::mapToResponse).toList();
    }

    @Cacheable(cacheNames = "catTipoProcesoById", key = "#id")
    public CatTipoProcesoDTO obtenerCatTipoProceso(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException("El id debe ser mayor a 0");
        }

        CatTipoProceso cat = repositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("CatTipoProceso", id));
        return mapToResponse(cat);
    }

    private CatTipoProcesoDTO mapToResponse(CatTipoProceso dom) {
        CatTipoProcesoDTO dto = new CatTipoProcesoDTO();
        dto.setId(dom.getId());
        dto.setNombre(dom.getNombre());
        return dto;
    }
}
