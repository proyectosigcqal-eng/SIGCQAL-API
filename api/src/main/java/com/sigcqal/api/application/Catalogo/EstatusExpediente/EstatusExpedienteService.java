package com.sigcqal.api.application.Catalogo.EstatusExpediente;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.Catalogo.EstatusExpediente.Model.EstatusExpediente;
import com.sigcqal.api.domain.Catalogo.EstatusExpediente.Port.EstatusExpedienteRepositoryPort;
import com.sigcqal.api.web.Catalogo.EstatusExpediente.Dto.EstatusExpedienteDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstatusExpedienteService {
    private final EstatusExpedienteRepositoryPort repositoryPort;

    @Cacheable(cacheNames = "estatusExpedienteAll", key = "'all'")
    public List<EstatusExpedienteDTO> obtenerEstatusExpedientes() {
        return repositoryPort.findAll().stream().map(this::mapToResponse).toList();
    }

    @Cacheable(cacheNames = "estatusExpedienteById", key = "#id")
    public EstatusExpedienteDTO obtenerEstatusExpediente(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidRequestException("El id debe ser mayor a 0");
        }

        EstatusExpediente item = repositoryPort.findById(id).orElseThrow(() -> new ResourceNotFoundException("EstatusExpediente", id));
        return mapToResponse(item);
    }

    private EstatusExpedienteDTO mapToResponse(EstatusExpediente dom) {
        EstatusExpedienteDTO dto = new EstatusExpedienteDTO();
        dto.setId(dom.getId());
        dto.setNombre(dom.getNombre());
        return dto;
    }
}
