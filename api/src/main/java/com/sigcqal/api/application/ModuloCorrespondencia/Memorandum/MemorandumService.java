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

import com.sigcqal.api.application.ModuloCorrespondencia.AcuseReciboInterno.AcuseReciboInternoService;

import com.sigcqal.api.web.ModuloCorrespondencia.AcuseReciboInterno.Dto.AcuseReciboInternoRequestDTO;

import jakarta.transaction.Transactional;

@Service
public class MemorandumService {

    @Autowired
    private MemorandumRepositoryPort repositoryPort;

   @Autowired
    private MemorandumMapper mapper; 

    @Autowired
    private AcuseReciboInternoService acuseService;



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

            // Línea 54: Verificamos si ya existe el acuse
        boolean yaExiste = acuseService.existePorMemorandum(saved.getId());

        if (!yaExiste) {
            // 1. Instanciar el objeto RequestDTO que el servicio espera
            AcuseReciboInternoRequestDTO acuseRequest = new AcuseReciboInternoRequestDTO();
            
            // 2. Llenar los datos usando el objeto 'saved' (que es el memo recién guardado)
            acuseRequest.setIdMemorandum(saved.getId());
            acuseRequest.setIdUsuarioRevisor(saved.getIdUsuarioFirmante());
            
            // 3. Llamar al método pasando el objeto completo, no los Long sueltos
            acuseService.crearAcuseAutomatico(acuseRequest);
        }

            // Continuar con el retorno normal del método
            return mapper.toResponse(saved);
                }

    public List<MemorandumResponseDTO> listarTodos() {
        List<Memorandum> memorandums = repositoryPort.findAll();
        
        if (memorandums == null) {
            return List.of(); 
        }


        return memorandums.stream()
            .filter(memo -> memo != null) 
            .map(mapper::toResponse) 
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