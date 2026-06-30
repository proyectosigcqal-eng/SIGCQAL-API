package com.sigcqal.api.application.ModuloAreaSustantiva.SentenciaEjecutoria;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sigcqal.api.application.ModuloAreaSustantiva.DemandaAmparo.EncabezadoHitoAmparoResolver;
import com.sigcqal.api.domain.ModuloAreaSustantiva.SentenciaEjecutoria.Port.SentenciaEjecutoriaRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.SentenciaEjecutoria.Mapper.SentenciaEjecutoriaMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.SentenciaEjecutoria.Dto.SentenciaEjecutoriaResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObtenerSentenciaEjecutoriaService implements ObtenerSentenciaEjecutoriaUseCase {

    private final SentenciaEjecutoriaRepositoryPort repositoryPort;
    private final SentenciaEjecutoriaMapper mapper;
    private final EncabezadoHitoAmparoResolver encabezadoResolver;

    @Override
    public List<SentenciaEjecutoriaResponseDTO> listarTodos() {
        return repositoryPort.findAll()
                .stream()
                .map(domain -> {
                    SentenciaEjecutoriaResponseDTO response = mapper.toResponse(domain);
                    encabezadoResolver.paraSentenciaEjecutoria(domain).ifPresent(response::setEncabezado);
                    return response;
                })
                .collect(Collectors.toList());
    }
}
