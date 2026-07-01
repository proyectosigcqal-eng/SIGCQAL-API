package com.sigcqal.api.application.ModuloAreaSustantiva.AudienciaCelebrada;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sigcqal.api.application.ModuloAreaSustantiva.DemandaAmparo.EncabezadoHitoAmparoResolver;
import com.sigcqal.api.domain.ModuloAreaSustantiva.AudienciaCelebrada.Port.AudienciaCelebradaRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.AudienciaCelebrada.Mapper.AudienciaCelebradaMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.AudienciaCelebrada.Dto.AudienciaCelebradaResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObtenerAudienciaCelebradaService implements ObtenerAudienciaCelebradaUseCase {

    private final AudienciaCelebradaRepositoryPort repositoryPort;
    private final AudienciaCelebradaMapper mapper;
    private final EncabezadoHitoAmparoResolver encabezadoResolver;

    @Override
    public List<AudienciaCelebradaResponseDTO> listarTodos() {
        return repositoryPort.findAll()
                .stream()
                .map(domain -> {
                    AudienciaCelebradaResponseDTO response = mapper.toResponse(domain);
                    encabezadoResolver.paraAudienciaCelebrada(domain).ifPresent(response::setEncabezado);
                    return response;
                })
                .collect(Collectors.toList());
    }
}
