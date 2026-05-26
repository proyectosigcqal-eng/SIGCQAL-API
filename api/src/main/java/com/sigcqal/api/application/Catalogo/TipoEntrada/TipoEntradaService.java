package com.sigcqal.api.application.Catalogo.TipoEntrada;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.Catalogo.TipoEntrada.Model.TipoEntrada;
import com.sigcqal.api.domain.Catalogo.TipoEntrada.Port.TipoEntradaRepositoryPort;
import com.sigcqal.api.web.Catalogo.TipoEntrada.Dto.TipoEntradaDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoEntradaService {
    private final TipoEntradaRepositoryPort repositoryPort;

    @Cacheable(cacheNames = "tipoEntradaAll", key = "'all'")
    public List<TipoEntradaDTO> obtenerTipoEntradas() {
        return repositoryPort.findAll().stream().map(this::mapToResponse).toList();
    }

    @Cacheable(cacheNames = "tipoEntradaById", key = "#id")
    public TipoEntradaDTO obtenerTipoEntrada(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException("El id debe ser mayor a 0");
        }

        TipoEntrada item = repositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("TipoEntrada", id));
        return mapToResponse(item);
    }

    private TipoEntradaDTO mapToResponse(TipoEntrada dom) {
        TipoEntradaDTO dto = new TipoEntradaDTO();
        dto.setId(dom.getId());
        dto.setNombre(dom.getNombre());
        return dto;
    }
}
