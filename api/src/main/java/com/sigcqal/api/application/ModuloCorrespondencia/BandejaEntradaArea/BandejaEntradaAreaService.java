package com.sigcqal.api.application.ModuloCorrespondencia.BandejaEntradaArea;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.domain.Catalogo.Area.Port.AreaRepositoryPort;
import com.sigcqal.api.domain.ModuloCorrespondencia.BandejaEntradaArea.Model.BandejaEntradaArea;
import com.sigcqal.api.domain.ModuloCorrespondencia.BandejaEntradaArea.Port.IBandejaEntradaAreaPort;
import com.sigcqal.api.domain.ModuloCorrespondencia.Correspondencia.Port.CorrespondenciaRepositoryPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.BandejaEntradaArea.Mapper.BandejaEntradaAreaMapper;
import com.sigcqal.api.web.ModuloCorrespondencia.BandejaEntradaArea.Dto.BandejaEntradaAreaRequestDTO;
import com.sigcqal.api.web.ModuloCorrespondencia.BandejaEntradaArea.Dto.BandejaEntradaAreaResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BandejaEntradaAreaService {
    private final IBandejaEntradaAreaPort port;
    private final BandejaEntradaAreaMapper mapper;
    private final AreaRepositoryPort areaRepositoryPort;
    private final CorrespondenciaRepositoryPort correspondenciaRepositoryPort;

    public BandejaEntradaAreaResponseDTO crearEntrada(BandejaEntradaAreaRequestDTO request) {
        if (request == null) {
            throw new InvalidRequestException("La solicitud no puede ser nula");
        }

        if (request.getIdCorrespondencia() == null || request.getIdCorrespondencia() <= 0) {
            throw new InvalidRequestException("El id de correspondencia es obligatorio");
        }

        if (request.getIdArea() == null || request.getIdArea() <= 0) {
            throw new InvalidRequestException("El id del área es obligatorio");
        }

        BandejaEntradaArea dom = new BandejaEntradaArea();
        dom.setIdCorrespondencia(request.getIdCorrespondencia());
        dom.setIdArea(request.getIdArea());
        dom.setIdUsuarioAsignado(request.getIdUsuarioAsignado());
        dom.setObservaciones(request.getObservaciones());
        dom.setEstatus("PENDIENTE");
        dom.setFechaAsignacion(LocalDateTime.now());

        BandejaEntradaArea saved = port.guardar(dom);

        var area = areaRepositoryPort.findById(saved.getIdArea()).orElse(null);
        var corr = correspondenciaRepositoryPort.findById(saved.getIdCorrespondencia()).orElse(null);

        return mapper.toResponse(
                saved,
                area != null ? area.getNombre() : null,
                corr != null ? corr.getFolioUnico() : null,
                corr != null ? corr.getAsunto() : null);
    }

    public List<BandejaEntradaAreaResponseDTO> listarPorArea(Long idArea) {
        if (idArea == null || idArea <= 0) {
            throw new InvalidRequestException("El id del área debe ser mayor a 0");
        }

        return port.listarPorArea(idArea).stream().map(this::enriquecer).toList();
    }

    public List<BandejaEntradaAreaResponseDTO> listarTodas() {
        return port.listarTodas().stream().map(this::enriquecer).toList();
    }

    private BandejaEntradaAreaResponseDTO enriquecer(BandejaEntradaArea bandeja) {
        var area = bandeja.getIdArea() != null ? areaRepositoryPort.findById(bandeja.getIdArea()).orElse(null) : null;
        var corr = bandeja.getIdCorrespondencia() != null
                ? correspondenciaRepositoryPort.findById(bandeja.getIdCorrespondencia()).orElse(null)
                : null;

        return mapper.toResponse(
                bandeja,
                area != null ? area.getNombre() : null,
                corr != null ? corr.getFolioUnico() : null,
                corr != null ? corr.getAsunto() : null);
    }
}

