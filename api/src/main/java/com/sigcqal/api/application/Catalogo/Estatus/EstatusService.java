package com.sigcqal.api.application.Catalogo.Estatus;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.Catalogo.Estatus.Model.Estatus;
import com.sigcqal.api.domain.Catalogo.Estatus.Port.EstatusRepositoryPort;
import com.sigcqal.api.web.Catalogo.Estatus.Dto.EstatusDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstatusService {

private final EstatusRepositoryPort repositoryPort;

    public List<EstatusDTO> obtenerEstatus() {
        return repositoryPort.listAll().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }
 
 
    private EstatusDTO mapToResponse(Estatus dom) {
        EstatusDTO res = new EstatusDTO();
        res.setNombreEstatus(dom.getNombreEstatus());
        res.setDescripcion(dom.getDescripcion());
        return res;
    }
}
