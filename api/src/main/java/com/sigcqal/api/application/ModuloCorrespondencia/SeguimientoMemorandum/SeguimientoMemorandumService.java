package com.sigcqal.api.application.ModuloCorrespondencia.SeguimientoMemorandum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoMemorandum.Model.SeguimientoMemorandum;
import com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoMemorandum.Port.ISeguimientoMemorandumPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoMemorandum.Mapper.SeguimientoMemorandumMapper;
import com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoMemorandum.Dto.SeguimientoMemorandumRequestDTO;
import com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoMemorandum.Dto.SeguimientoMemorandumResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeguimientoMemorandumService {

    @Autowired
    private ISeguimientoMemorandumPort port;

    @Autowired
    private SeguimientoMemorandumMapper mapper;

    public SeguimientoMemorandumResponseDTO guardar(SeguimientoMemorandumRequestDTO request) {
        SeguimientoMemorandum seguimiento = new SeguimientoMemorandum();

        seguimiento.setIdSeguimientoMemorandum(request.getIdSeguimientoMemorandum());
        seguimiento.setIdMemo(request.getIdMemo());
        seguimiento.setRespuestaSeguimientoMemorandum(request.getRespuestaSeguimientoMemorandum());
        seguimiento.setArchivoAdjunto(request.getArchivoAdjunto());
        seguimiento.setIdUsuario(request.getIdUsuario());
        seguimiento.setIdEstatus(request.getIdEstatus());

        if (request.getFechaResolucion() != null && !request.getFechaResolucion().isBlank()) {
            seguimiento.setFechaResolucion(LocalDate.parse(request.getFechaResolucion()));
        }
        if (request.getHoraResolucion() != null && !request.getHoraResolucion().isBlank()) {
            seguimiento.setHoraResolucion(LocalTime.parse(request.getHoraResolucion()));
        }

        seguimiento.setFechaRegistro(LocalDateTime.now());

        var saved = port.guardar(seguimiento);
        return mapper.toResponse(saved);
    }

    public List<SeguimientoMemorandumResponseDTO> listarTodos() {
        return port.listarTodos()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<SeguimientoMemorandumResponseDTO> listarPorMemorandumId(Long idMemo) {
        return port.listarPorMemorandumId(idMemo)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}

