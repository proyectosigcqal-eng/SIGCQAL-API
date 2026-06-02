package com.sigcqal.api.application.Catalogo.Direccion;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.domain.Catalogo.Direccion.Model.Direccion;
import com.sigcqal.api.domain.Catalogo.Direccion.Port.DireccionRepositoryPort;
import com.sigcqal.api.infra.Catalogo.Direccion.Mapper.DireccionMapper;
import com.sigcqal.api.web.Catalogo.Direccion.Dto.DireccionDTO;
import com.sigcqal.api.web.Catalogo.Direccion.Dto.DireccionRequestDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DireccionService {

    private final DireccionRepositoryPort port;
    private final DireccionMapper mapper;

    @Transactional
    public DireccionDTO guardar(DireccionRequestDto request) {
        Direccion direccion = Direccion.builder()
                .calle(request.getCalle())
                .numExt(request.getNumExt())
                .numInt(request.getNumInt())
                .colonia(request.getColonia())
                .cp(request.getCp())
                .idMunicipio(request.getIdMunicipio())
                .idEstado(request.getIdEstado())
                .build();

        return mapper.toResponse(port.save(direccion));
    }

    public DireccionDTO obtenerPorId(Long id) {
        return port.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new InvalidRequestException(
                        "Dirección no encontrada con id: " + id));
    }

    public List<DireccionDTO> obtenerTodas() {
        return port.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public DireccionDTO actualizar(Long id, DireccionRequestDto request) {
        Direccion existente = port.findById(id)
                .orElseThrow(() -> new InvalidRequestException(
                        "Dirección no encontrada con id: " + id));

        existente.setCalle(request.getCalle());
        existente.setNumExt(request.getNumExt());
        existente.setNumInt(request.getNumInt());
        existente.setColonia(request.getColonia());
        existente.setCp(request.getCp());
        existente.setIdMunicipio(request.getIdMunicipio());
        existente.setIdEstado(request.getIdEstado());

        return mapper.toResponse(port.save(existente));
    }

}