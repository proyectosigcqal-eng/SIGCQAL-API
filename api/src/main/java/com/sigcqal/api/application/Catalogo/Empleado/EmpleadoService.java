package com.sigcqal.api.application.Catalogo.Empleado;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.Catalogo.Empleado.Model.Empleado;
import com.sigcqal.api.domain.Catalogo.Empleado.Port.EmpleadoRepositoryPort;
import com.sigcqal.api.web.Catalogo.Empleado.Dto.EmpleadoDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpleadoService {
    private final EmpleadoRepositoryPort repositoryPort;

    @Cacheable(cacheNames = "empleadosAll", key = "'all'")
    public List<EmpleadoDTO> obtenerEmpleados() {
        return repositoryPort.findAll().stream().map(this::mapToResponse).toList();
    }

    @Cacheable(cacheNames = "empleadosById", key = "#id")
    public EmpleadoDTO obtenerEmpleado(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException("El id debe ser mayor a 0");
        }

        Empleado emp = repositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("Empleado", id));
        return mapToResponse(emp);
    }

    private EmpleadoDTO mapToResponse(Empleado dom) {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId(dom.getId());
        dto.setNombreCompleto(dom.getNombreCompleto());
        dto.setCargo(dom.getCargo());
        dto.setIdArea(dom.getIdArea());
        return dto;
    }
}
