package com.sigcqal.api.application.ModuloCorrespondencia.SeguimientoOficio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoOficio.Model.SeguimientoOficio;
import com.sigcqal.api.domain.ModuloCorrespondencia.SeguimientoOficio.Port.SeguimientoOficioPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.SeguimientoOficio.Mapper.SeguimientoOficioMapper;
import com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoOficio.Dto.SeguimientoOficioRequestDTO;
import com.sigcqal.api.web.ModuloCorrespondencia.SeguimientoOficio.Dto.SeguimientoOficioResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeguimientoOficioService {

    @Autowired
    private SeguimientoOficioPort port;

    @Autowired
    private SeguimientoOficioMapper mapper;

    public SeguimientoOficioResponseDTO guardar(SeguimientoOficioRequestDTO request) {
        SeguimientoOficio seguimientoOficio = new SeguimientoOficio();

        seguimientoOficio.setIdSeguimientoOficio(request.getIdSeguimientoOficio());
        seguimientoOficio.setIdOficio(request.getIdOficio());
        seguimientoOficio.setFolioRespuesta(request.getFolioRespuesta());
        seguimientoOficio.setRespuestasSeguimientoOficio(request.getRespuestasSeguimientoOficio());
        seguimientoOficio.setIdUsuario(request.getIdUsuario());
        seguimientoOficio.setIdEstatus(request.getIdEstatus());

        if (request.getArchivoAdjunto() != null && !request.getArchivoAdjunto().isEmpty()) {
            String nombreArchivo = request.getArchivoAdjunto().getOriginalFilename();
            seguimientoOficio.setArchivoAdjunto(nombreArchivo);
        }

        if (request.getFechaResolucion() != null && !request.getFechaResolucion().isBlank()) {
            seguimientoOficio.setFechaResolucion(LocalDate.parse(request.getFechaResolucion()));
        }
        if (request.getHoraResolucion() != null && !request.getHoraResolucion().isBlank()) {
            seguimientoOficio.setHoraResolucion(LocalTime.parse(request.getHoraResolucion()));
        }

        seguimientoOficio.setFechaRegistro(LocalDateTime.now());

        var saved = port.guardar(seguimientoOficio);
        return mapper.toResponse(saved);
    }

    public List<SeguimientoOficioResponseDTO> listarTodos() {
        return port.listarTodos()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<SeguimientoOficioResponseDTO> listarPorOficioId(Integer idOficio) {
        return port.listarPorOficioId(idOficio)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}