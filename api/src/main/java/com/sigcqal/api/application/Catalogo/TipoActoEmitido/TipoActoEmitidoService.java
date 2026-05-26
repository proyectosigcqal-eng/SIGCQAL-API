package com.sigcqal.api.application.Catalogo.TipoActoEmitido;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.Catalogo.TipoActoEmitido.Model.TipoActoEmitido;
import com.sigcqal.api.domain.Catalogo.TipoActoEmitido.Port.TipoActoEmitidoRepositoryPort;
import com.sigcqal.api.web.Catalogo.TipoActoEmitido.Dto.TipoActoEmitidoDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoActoEmitidoService {
    private final TipoActoEmitidoRepositoryPort repositoryPort;

    @Cacheable(cacheNames = "tipoActoEmitidoAll", key = "'all'")
    public List<TipoActoEmitidoDTO> obtenerTipoActoEmitidos() {
        return repositoryPort.findAll().stream().map(this::mapToResponse).toList();
    }

    @Cacheable(cacheNames = "tipoActoEmitidoById", key = "#id")
    public TipoActoEmitidoDTO obtenerTipoActoEmitido(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException("El id debe ser mayor a 0");
        }

        TipoActoEmitido item = repositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("TipoActoEmitido", id));
        return mapToResponse(item);
    }

    private TipoActoEmitidoDTO mapToResponse(TipoActoEmitido dom) {
        TipoActoEmitidoDTO dto = new TipoActoEmitidoDTO();
        dto.setId(dom.getId());
        dto.setNombre(dom.getNombre());
        return dto;
    }
}
