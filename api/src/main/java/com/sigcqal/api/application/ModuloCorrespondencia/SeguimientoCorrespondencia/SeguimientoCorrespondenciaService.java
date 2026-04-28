package com.sigcqal.api.application.ModuloCorrespondencia.SeguimientoCorrespondencia;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoCorrespondencia.Model.SeguimientoCorrespondencia;
import com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoCorrespondencia.Port.ISeguimientoCorrespondenciaPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoCorrespondencia.Mapper.SeguimientoCorrespondenciaMapper;
import com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoCorrespondencia.Dto.SeguimientoCorrespondenciaRequestDTO;
import com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoCorrespondencia.Dto.SeguimientoCorrespondenciaResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeguimientoCorrespondenciaService {

    @Autowired
    private ISeguimientoCorrespondenciaPort port;

    @Autowired
    private SeguimientoCorrespondenciaMapper mapper;

    public SeguimientoCorrespondenciaResponseDTO guardar(SeguimientoCorrespondenciaRequestDTO request) {
        SeguimientoCorrespondencia seguimientoCorrespondencia = new SeguimientoCorrespondencia();

        seguimientoCorrespondencia.setIdSeguimientoCorrespondencia(request.getIdSeguimientoCorrespondencia());
        seguimientoCorrespondencia.setIdCorrespondencia(request.getIdCorrespondencia());
        seguimientoCorrespondencia.setFolioRespuesta(request.getFolioRespuesta());
        seguimientoCorrespondencia.setRespuestaSeguimientoCorrespondencia(request.getRespuestaSeguimientoCorrespondencia());
        seguimientoCorrespondencia.setArchivoAdjunto(request.getArchivoAdjunto());
        seguimientoCorrespondencia.setIdUsuario(request.getIdUsuario());
        seguimientoCorrespondencia.setIdEstatus(request.getIdEstatus());

        if (request.getFechaResolucion() != null && !request.getFechaResolucion().isBlank()) {
            seguimientoCorrespondencia.setFechaResolucion(LocalDate.parse(request.getFechaResolucion()));
        }
        if (request.getHoraResolucion() != null && !request.getHoraResolucion().isBlank()) {
            seguimientoCorrespondencia.setHoraResolucion(LocalTime.parse(request.getHoraResolucion()));
        }

        seguimientoCorrespondencia.setFechaRegistro(LocalDateTime.now());

        var saved = port.guardar(seguimientoCorrespondencia);
        return mapper.toResponse(saved);
    }

    public List<SeguimientoCorrespondenciaResponseDTO> listarTodos() {
        return port.listarTodos()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<SeguimientoCorrespondenciaResponseDTO> listarPorCorrespondenciaId(Integer idCorrespondencia) {
        return port.listarPorCorrespondenciaId(idCorrespondencia)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
