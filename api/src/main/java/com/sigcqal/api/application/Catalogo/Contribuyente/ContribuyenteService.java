package com.sigcqal.api.application.Catalogo.Contribuyente;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Model.Contribuyente;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Port.ContribuyenteRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Mapper.ContribuyenteMapper;
import com.sigcqal.api.web.Catalogo.Contribuyente.Dto.ContribuyenteDto;
import com.sigcqal.api.web.Catalogo.Contribuyente.Dto.ContribuyenteRequestDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContribuyenteService {

    private final ContribuyenteRepositoryPort port;
    private final ContribuyenteMapper mapper;

    @Transactional
    public ContribuyenteDto guardar(ContribuyenteRequestDto request) {
        Contribuyente contribuyente = Contribuyente.builder()
                .idPersona(request.getIdPersona().intValue())
                .fechaRegistroSistema(LocalDateTime.now())
                .observacionesInternas(request.getObservacionesInternas())
                .build();

        return mapper.toResponse(port.save(contribuyente));
    }

    public ContribuyenteDto obtenerPorId(Long id) {
        return port.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new InvalidRequestException(
                        "Contribuyente no encontrado con id: " + id));
    }

    public List<ContribuyenteDto> obtenerTodos() {
        return port.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ContribuyenteDto actualizar(Long id, ContribuyenteRequestDto request) {
        Contribuyente existente = port.findById(id)
                .orElseThrow(() -> new InvalidRequestException(
                        "Contribuyente no encontrado con id: " + id));

        existente.setIdPersona(request.getIdPersona().intValue());
        existente.setObservacionesInternas(request.getObservacionesInternas());

        return mapper.toResponse(port.save(existente));
    }

}