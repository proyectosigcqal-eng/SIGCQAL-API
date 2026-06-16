package com.sigcqal.api.application.ModuloAreaSustantiva.Queja;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Queja.Port.QuejaRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Mapper.QuejaMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.Queja.DTO.QuejaResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuejaService {

    private final QuejaRepositoryPort port;
    private final QuejaMapper mapper;

    public QuejaResponseDTO obtenerPorId(Integer idQueja) {
        return port.findById(idQueja)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Queja no encontrada con el ID: " + idQueja));
    }

    public List<QuejaResponseDTO> listarTodas() {
        return port.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}