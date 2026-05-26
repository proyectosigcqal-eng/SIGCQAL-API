package com.sigcqal.api.application.Catalogo.Direccion;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.Catalogo.Direccion.Model.Direccion;
import com.sigcqal.api.domain.Catalogo.Direccion.Port.DireccionRepositoryPort;
import com.sigcqal.api.web.Catalogo.Direccion.Dto.DireccionDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DireccionService {
    private final DireccionRepositoryPort repositoryPort;

    @Cacheable(cacheNames = "direccionesAll", key = "'all'")
    public List<DireccionDTO> obtenerDirecciones() {
        return repositoryPort.findAll().stream().map(this::mapToResponse).toList();
    }

    @Cacheable(cacheNames = "direccionesById", key = "#id")
    public DireccionDTO obtenerDireccion(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException("El id debe ser mayor a 0");
        }

        Direccion dir = repositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("Dirección", id));
        return mapToResponse(dir);
    }

    private DireccionDTO mapToResponse(Direccion dom) {
        DireccionDTO dto = new DireccionDTO();
        dto.setId(dom.getId());
        dto.setCalle(dom.getCalle());
        dto.setNumExt(dom.getNumExt());
        dto.setNumInt(dom.getNumInt());
        dto.setColonia(dom.getColonia());
        dto.setCp(dom.getCp());
        dto.setIdMunicipio(dom.getIdMunicipio());
        dto.setIdEstado(dom.getIdEstado());
        return dto;
    }
}
