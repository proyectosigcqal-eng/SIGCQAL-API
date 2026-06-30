package com.sigcqal.api.application.ModuloAreaSustantiva.AudienciaEspera;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaEspera.Port.AudienciaEsperaRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.AudienciaEspera.Mapper.AudienciaEsperaMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.AudienciaEspera.Dto.AudienciaEsperaResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObtenerAudienciaEsperaService implements ObtenerAudienciaEsperaUseCase {

    private final AudienciaEsperaRepositoryPort repositoryPort;
    private final AudienciaEsperaMapper mapper;

    @Override
    public List<AudienciaEsperaResponseDTO> listarTodos() {
        return repositoryPort.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
