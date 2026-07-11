package com.sigcqal.api.application.ModuloCorrespondencia.AcuseCorrespondencia;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// DESPUÉS
import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseCorrespondencia.Model.AcuseCorrespondencia;
import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseCorrespondencia.Port.AcuseCorrespondenciaRepositoryPort;
import com.sigcqal.api.domain.ModuloCorrespondencia.Correspondencia.Model.Correspondencia;
import com.sigcqal.api.domain.ModuloCorrespondencia.Correspondencia.Port.CorrespondenciaRepositoryPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseCorrespondencia.Mapper.AcuseCorrespondenciaMapper;
import com.sigcqal.api.web.ModuloCorrespondencia.AcuseCorrespondencia.Dto.*;
import com.sigcqal.api.web.ModuloCorrespondencia.AcuseOficio.Dto.AcuseOficioResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AcuseCorrespondenciaService {

    private final AcuseCorrespondenciaRepositoryPort repository;
    private final AcuseCorrespondenciaMapper mapper;
    private final CorrespondenciaRepositoryPort correspondenciaRepositoryPort;

    // DESPUÉS
    @Transactional
    public void crear(AcuseCorrespondenciaRequestDTO request) {
        AcuseCorrespondencia acuse = new AcuseCorrespondencia();

        acuse.setIdCorrespondencia(request.getIdCorrespondencia());
        acuse.setIdUsuarioRevisor(request.getIdUsuarioRevisor());
        acuse.setEsDelArea(request.getEsDelArea());

        acuse.setFechaAceptacion(LocalDate.now());
        acuse.setHoraAceptacion(LocalTime.now());

        repository.save(acuse);

        // 👇 NUEVO: actualizar estatus de correspondencia a "4 EN SEGUIMIENTO"
        correspondenciaRepositoryPort.findById(request.getIdCorrespondencia())
                .ifPresent(correspondencia -> {
                    correspondencia.setIdEstatus(4L);
                    correspondenciaRepositoryPort.save(correspondencia);
                });
    }

    public List<AcuseCorrespondenciaResponseDTO> listarPorArea(Long idArea) {
        return repository.findByArea(idArea)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<AcuseCorrespondenciaResponseDTO> listarPorCorrespondencia(Long idCorrespondencia) {
        return repository.findByCorrespondencia(idCorrespondencia)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<AcuseCorrespondenciaResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}