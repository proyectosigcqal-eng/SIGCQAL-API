package com.sigcqal.api.application.Catalogo.Estado;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.Catalogo.Estado.Model.Estado;
import com.sigcqal.api.domain.Catalogo.Estado.Port.EstadoRepositoryPort;
import com.sigcqal.api.web.Catalogo.Estado.Dto.EstadoDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstadoService {

private final EstadoRepositoryPort repositoryPort;

    public List<EstadoDTO> obtenerEstados() {
        return repositoryPort.ListAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private EstadoDTO mapToResponse(Estado dom) {
        EstadoDTO res = new EstadoDTO();
        res.setNombreEstado(dom.getNombreEstado());
        return res;
    }
}
    



