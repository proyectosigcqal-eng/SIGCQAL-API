package com.sigcqal.api.application.Catalogo.TipoCorrespondencia;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.Catalogo.TipoCorrespondencia.Model.TipoCorrespondencia;
import com.sigcqal.api.domain.Catalogo.TipoCorrespondencia.Port.TipoCorrespondenciaRepositoryPort;
import com.sigcqal.api.web.Catalogo.TipoCorrespondencia.Dto.TipoCorrespondenciaDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoCorrespondenciaService {
    private final TipoCorrespondenciaRepositoryPort repositoryPort;

    @Cacheable(cacheNames = "tiposCorrespondenciaAll", key = "'all'")
    public List<TipoCorrespondenciaDTO> obtenerTipos() {
        return repositoryPort.findAll().stream().map(this::mapToResponse).toList();
    }

    @Cacheable(cacheNames = "tiposCorrespondenciaById", key = "#id")
    public TipoCorrespondenciaDTO obtenerPorId(Integer id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException("El id debe ser mayor a 0");
        }

        TipoCorrespondencia dom = repositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoCorrespondencia", id.longValue()));
        return mapToResponse(dom);
    }

    private TipoCorrespondenciaDTO mapToResponse(TipoCorrespondencia dom) {
        TipoCorrespondenciaDTO dto = new TipoCorrespondenciaDTO();
        dto.setIdTipo(dom.getIdTipo());
        dto.setIdNatural(dom.getIdNatural());
        dto.setDescripcion(dom.getDescripcion());
        return dto;
    }
}
