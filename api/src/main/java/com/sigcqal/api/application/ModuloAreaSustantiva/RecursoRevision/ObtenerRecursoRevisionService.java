package com.sigcqal.api.application.ModuloAreaSustantiva.RecursoRevision;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sigcqal.api.application.ModuloAreaSustantiva.DemandaAmparo.EncabezadoHitoAmparoResolver;
import com.sigcqal.api.domain.ModuloAreaSustantiva.RecursoRevision.Port.RecursoRevisionRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.RecursoRevision.Mapper.RecursoRevisionMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.RecursoRevision.Dto.RecursoRevisionResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ObtenerRecursoRevisionService implements ObtenerRecursoRevisionUseCase {

    private final RecursoRevisionRepositoryPort repositoryPort;
    private final RecursoRevisionMapper mapper;
    private final EncabezadoHitoAmparoResolver encabezadoResolver;

    @Override
    public List<RecursoRevisionResponseDTO> listarTodos() {
        return repositoryPort.findAll()
                .stream()
                .map(domain -> {
                    RecursoRevisionResponseDTO response = mapper.toResponse(domain);
                    encabezadoResolver.paraRecursoRevision(domain).ifPresent(response::setEncabezado);
                    return response;
                })
                .collect(Collectors.toList());
    }
}
