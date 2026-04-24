package com.sigcqal.api.application.ModuloCorrespondencia.AcuseReciboInterno;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseReciboInterno.Port.AcuseReciboInternoRepositoryPort;
import com.sigcqal.api.web.ModuloCorrespondencia.AcuseReciboInterno.Dto.*;

import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseReciboInterno.Model.AcuseReciboInterno;

import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.sigcqal.api.infra.ModuloCorrespondencia.AcuseReciboInterno.Mapper.AcuseReciboInternoMapper;

@Service
@RequiredArgsConstructor
public class AcuseReciboInternoService {

    @Autowired
    private AcuseReciboInternoRepositoryPort repository;

    @Autowired
    private AcuseReciboInternoMapper mapper;
    // LISTA DE MEMOS
    public List<AcuseReciboInternoResponseDTO> listarPorUsuario(Long idUsuario) {
        List<AcuseReciboInternoResponseDTO> lista = repository.findByUsuario(idUsuario)
            .stream()
            .map(mapper::toResponse)
            .collect(Collectors.toList());

    if (lista.isEmpty()) {
        throw new RuntimeException("No se encontraron acuses de recibo para el usuario con ID: " + idUsuario);
    }

    return lista;
    }

    // DETALLE
    public AcuseReciboInternoResponseDTO obtenerDetalle(Long idAcuse) {
        var acuse = repository.findById(idAcuse)
                .orElseThrow(() -> new RuntimeException("Acuse no encontrado"));

        return mapper.toResponse(acuse);
    }

    // RESPONDER
    public void responder(AcuseReciboInternoRequestDTO request) {
    // Creamos una nueva instancia desde cero
    AcuseReciboInterno acuse = new AcuseReciboInterno();
    
    // Seteamos los datos enviados desde Postman
    acuse.setEsDelArea(request.getEsDelArea());
    acuse.setIdMemorandum(request.getIdMemorandum());
    acuse.setIdUsuarioRevisor(request.getIdUsuarioRevisor());
    
    // Asignamos la fecha y hora automáticamente
    acuse.setFechaAceptacion(LocalDate.now());
    acuse.setHoraAceptacion(LocalTime.now());

    // Guardamos (el ID se generará solo en la base de datos)
    repository.save(acuse);
}

public List<AcuseReciboInternoResponseDTO> listarPorArea(Long idArea) {
    return repository.findByArea(idArea)
            .stream()
            .map(mapper::toResponse)
            .collect(Collectors.toList());

        }


@Transactional
    public AcuseReciboInternoResponseDTO crearAcuseAutomatico(AcuseReciboInternoRequestDTO request) {

    AcuseReciboInterno acuse = new AcuseReciboInterno();

    acuse.setIdMemorandum(request.getIdMemorandum());
    acuse.setIdUsuarioRevisor(request.getIdUsuarioRevisor());
    acuse.setEsDelArea(null);

   
            //acuse.setIdCorrespondencia(request.getIdCorrespondencia());
            acuse.setNumMemo(request.getNumMemo());
            acuse.setFechaEmision(request.getFechaEmision());
            acuse.setIdUsuarioEmisor(request.getIdUsuarioEmisor());
            acuse.setFolioUnico(request.getFolioUnico());
            acuse.setObservaciones(request.getObservaciones());
            acuse.setUrlMemorandumGenerado(request.getUrlMemorandumGenerado());
            acuse.setIdPlantilla(request.getIdPlantilla());
            acuse.setIdUsuarioFirmante(request.getIdUsuarioFirmante());


    
    AcuseReciboInterno saved = repository.save(acuse);

    return mapper.toResponse(saved);
}

public boolean existePorMemorandum(Long idMemorandum) {
    return repository.existePorMemorandum(idMemorandum);
}


}