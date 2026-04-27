package com.sigcqal.api.application.ModuloCorrespondencia.AcuseCorrespondencia;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseCorrespondencia.Model.AcuseCorrespondencia;
import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseCorrespondencia.Port.AcuseCorrespondenciaRepositoryPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseCorrespondencia.Mapper.AcuseCorrespondenciaMapper;
import com.sigcqal.api.web.ModuloCorrespondencia.AcuseCorrespondencia.Dto.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AcuseCorrespondenciaService {

    private final AcuseCorrespondenciaRepositoryPort repository;
    private final AcuseCorrespondenciaMapper mapper;

  
    public void crear(AcuseCorrespondenciaRequestDTO request) {

        AcuseCorrespondencia acuse = new AcuseCorrespondencia();

        acuse.setIdCorrespondencia(request.getIdCorrespondencia());
        acuse.setIdUsuarioRevisor(request.getIdUsuarioRevisor());
        acuse.setEsDelArea(request.getEsDelArea());

        acuse.setFechaAceptacion(LocalDate.now());
        acuse.setHoraAceptacion(LocalTime.now());

        repository.save(acuse);
    }

    public List<AcuseCorrespondenciaResponseDTO> listarPorArea(Long idArea) {
        return repository.findByArea(idArea)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}