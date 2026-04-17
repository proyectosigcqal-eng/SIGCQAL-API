package com.sigcqal.api.application.ModuloCorrespondencia.AcuseReciboInterno;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseReciboInterno.Port.AcuseReciboInternoRepositoryPort;
import com.sigcqal.api.web.ModuloCorrespondencia.AcuseReciboInterno.Dto.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AcuseReciboInternoService {

    private final AcuseReciboInternoRepositoryPort repository;

    // ✅ 1. LISTA DE MEMOS
    public List<AcuseReciboInternoResponseDTO> listarPorUsuario(Long idUsuario) {
        return repository.findByUsuario(idUsuario)
                .stream()
                .map(AcuseReciboInternoResponseDTO::fromDomain)
                .collect(Collectors.toList());
    }

    // ✅ 2. DETALLE
    public AcuseReciboInternoResponseDTO obtenerDetalle(Long idAcuse) {
        var acuse = repository.findById(idAcuse)
                .orElseThrow(() -> new RuntimeException("Acuse no encontrado"));

        return AcuseReciboInternoResponseDTO.fromDomain(acuse);
    }

    // ✅ 3. RESPONDER
    public void responder(AcuseReciboInternoRequestDTO request) {

        var acuse = repository.findById(request.getIdAcuse())
                .orElseThrow(() -> new RuntimeException("Acuse no encontrado"));

        acuse.setEsDelArea(request.getEsDelArea());
        acuse.setFechaAceptacion(LocalDate.now());
        acuse.setHoraAceptacion(LocalTime.now());

        repository.save(acuse);
    }
}