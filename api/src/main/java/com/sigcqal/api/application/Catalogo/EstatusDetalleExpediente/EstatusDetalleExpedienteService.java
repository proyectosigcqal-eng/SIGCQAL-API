package com.sigcqal.api.application.Catalogo.EstatusDetalleExpediente;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.Catalogo.EstatusDetalleExpediente.Model.EstatusDetalleExpediente;
import com.sigcqal.api.domain.Catalogo.EstatusDetalleExpediente.Port.EstatusDetalleExpedienteRepositoryPort;
import com.sigcqal.api.web.Catalogo.EstatusDetalleExpediente.Dto.EstatusDetalleExpedienteDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstatusDetalleExpedienteService {
    private final EstatusDetalleExpedienteRepositoryPort repositoryPort;

    @Cacheable(cacheNames = "estatusDetalleExpedienteAll", key = "'all'")
    public List<EstatusDetalleExpedienteDTO> obtenerEstatusDetalleExpedientes() {
        return repositoryPort.findAll().stream().map(this::mapToResponse).toList();
    }

    @Cacheable(cacheNames = "estatusDetalleExpedienteById", key = "#id")
    public EstatusDetalleExpedienteDTO obtenerEstatusDetalleExpediente(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException("El id debe ser mayor a 0");
        }

        EstatusDetalleExpediente item = repositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("EstatusDetalleExpediente", id));
        return mapToResponse(item);
    }

    private EstatusDetalleExpedienteDTO mapToResponse(EstatusDetalleExpediente dom) {
        EstatusDetalleExpedienteDTO dto = new EstatusDetalleExpedienteDTO();
        dto.setId(dom.getId());
        dto.setNombre(dom.getNombre());
        return dto;
    }
}
