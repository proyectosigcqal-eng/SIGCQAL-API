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
    private MemorandumMapper Mapper; 

    @Transactional
    public MemorandumResponseDTO generarMemorandum(MemorandumRequestDTO request) {

        Memorandum memo = new Memorandum();
        memo.setIdCorrespondencia(request.getIdCorrespondencia());
        memo.setIdPlantilla(request.getIdPlantilla());
        memo.setIdUsuarioEmisor(request.getIdUsuarioEmisor());
        memo.setIdUsuarioFirmante(request.getIdUsuarioFirmante());
        memo.setInstruccionSeguimiento(request.getInstruccionSeguimiento());
        memo.setObservaciones(request.getObservaciones());
        
       
        validarCampos(memo);
        memo.setFolioUnico(generarFolioSeguro());

     
        Memorandum saved = repositoryPort.save(memo);

       
        return Mapper.toResponse(saved);
    }

    public List<MemorandumResponseDTO> listarTodos() {
        List<Memorandum> memorandums = repositoryPort.findAll();
        
        if (memorandums == null) {
            return List.of(); 
        }


        return memorandums.stream()
            .filter(memo -> memo != null) 
            .map(Mapper::toResponse) 
            .collect(Collectors.toList());
    }

    private String generarFolioSeguro() {
        String nuevoFolio;
        do {
            nuevoFolio = "MEMO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (repositoryPort.existeFolio(nuevoFolio)); 
        return nuevoFolio;
    }

    private void validarCampos(Memorandum memo) {
        if (memo.getIdCorrespondencia() == null) {
            throw new IllegalArgumentException("El campo Correspondencia es obligatorio");
        }
    }
}