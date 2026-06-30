package com.sigcqal.api.application.ModuloAreaSustantiva.AudienciaCelebrada;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaCelebrada.Port.AudienciaCelebradaRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.AudienciaCelebrada.Mapper.AudienciaCelebradaMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.AudienciaCelebrada.Dto.AudienciaCelebradaResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObtenerAudienciaCelebradaService implements ObtenerAudienciaCelebradaUseCase {

    private final AudienciaCelebradaRepositoryPort repositoryPort;
    private final AudienciaCelebradaMapper mapper;

    @Override
    public List<AudienciaCelebradaResponseDTO> listarTodos() {
        return repositoryPort.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
