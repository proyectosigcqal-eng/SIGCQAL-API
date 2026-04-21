package com.sigcqal.api.application.ModuloCorrespondencia.Memorandum;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.ModuloCorrespondencia.Memorandum.Model.Memorandum;
import com.sigcqal.api.domain.ModuloCorrespondencia.Memorandum.Port.MemorandumRepositoryPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.Memorandum.Mapper.MemorandumMapper;
import com.sigcqal.api.web.ModuloCorrespondencia.Memorandum.Dto.MemorandumRequestDTO;
import com.sigcqal.api.web.ModuloCorrespondencia.Memorandum.Dto.MemorandumResponseDTO;

import jakarta.transaction.Transactional;

@Service
public class MemorandumService {

    @Autowired
    private MemorandumRepositoryPort repositoryPort;

   @Autowired
    private MemorandumMapper mapper; 

    @Transactional
    public MemorandumResponseDTO guardarMemorandum(MemorandumRequestDTO request) {

        Memorandum memo = new Memorandum();
        memo.setIdCorrespondencia(request.getIdCorrespondencia());
        memo.setIdPlantilla(request.getIdPlantilla());
        memo.setIdUsuarioEmisor(request.getIdUsuarioEmisor());
        memo.setIdUsuarioFirmante(request.getIdUsuarioFirmante());
        memo.setInstruccionSeguimiento(request.getInstruccionSeguimiento());
        memo.setObservaciones(request.getObservaciones());
        memo.setFolioUnico(generarFolioSeguro());
        memo.setUrlSolicitudMemorandum(request.getUrlSolicitudMemorandum());
        memo.setIdArea(request.getIdArea());

     
        Memorandum saved = repositoryPort.save(memo);
        return mapper.toResponse(saved);
    }

    public List<MemorandumResponseDTO> listarMemorandum() {
        List<Memorandum> memorandums = repositoryPort.findAll();
        
        if (memorandums == null) {
            return List.of(); 
        }


        return memorandums.stream()
            .filter(memo -> memo != null) 
            .map(mapper::toResponse) 
            .collect(Collectors.toList());
    }

public List<MemorandumResponseDTO> listarPorArea(Long idArea) {
    List<Memorandum> memorandums = repositoryPort.findByArea(idArea);
    
    if (memorandums == null) {
        return List.of();
    }

    return memorandums.stream()
        .filter(memo -> memo != null)
        .map(mapper::toResponse)
        .collect(Collectors.toList());
}

public MemorandumResponseDTO buscarPorId(Long id) {
    return repositoryPort.buscarPorId(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new RuntimeException("No se encontró el memorandum con ID: " + id));
}

    private String generarFolioSeguro() {
        String nuevoFolio;
        do {
            nuevoFolio = "MEMO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (repositoryPort.existeFolio(nuevoFolio)); 
        return nuevoFolio;
    }

}