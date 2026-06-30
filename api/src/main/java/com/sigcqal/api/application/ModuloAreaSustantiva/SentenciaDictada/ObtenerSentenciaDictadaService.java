package com.sigcqal.api.application.ModuloAreaSustantiva.SentenciaDictada;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaDictada.Port.SentenciaDictadaRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.SentenciaDictada.Mapper.SentenciaDictadaMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.SentenciaDictada.Dto.SentenciaDictadaResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObtenerSentenciaDictadaService implements ObtenerSentenciaDictadaUseCase {

    private final SentenciaDictadaRepositoryPort repositoryPort;
    private final SentenciaDictadaMapper mapper;

    @Override
    public List<SentenciaDictadaResponseDTO> listarTodos() {
        return repositoryPort.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
