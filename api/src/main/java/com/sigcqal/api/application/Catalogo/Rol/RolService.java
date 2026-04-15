package com.sigcqal.api.application.Catalogo.Rol;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.Catalogo.Rol.Model.Rol;
import com.sigcqal.api.domain.Catalogo.Rol.Port.RolRepositoryPort;
import com.sigcqal.api.web.Catalogo.Rol.Dto.RolDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolService {
    private final RolRepositoryPort repositoryPort;

    @Cacheable(cacheNames = "rolesAll", key = "'all'")
    public List<RolDTO> obtenerRoles() {
        return repositoryPort.findAll().stream().map(this::mapToResponse).toList();
    }

    @Cacheable(cacheNames = "rolesById", key = "#id")
    public RolDTO obtenerRol(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException("El id debe ser mayor a 0");
        }

        Rol rol = repositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rol", id));
        return mapToResponse(rol);
    }

    private RolDTO mapToResponse(Rol dom) {
        RolDTO dto = new RolDTO();
        dto.setId(dom.getId());
        dto.setNombre(dom.getNombre());
        dto.setDescripcion(dom.getDescripcion());
        return dto;
    }
}
