package com.sigcqal.api.application.ModuloCorrespondencia.Memorandum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigcqal.api.application.ModuloCorrespondencia.Documento.GeneradorDocumentoService;
import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.domain.ModuloCorrespondencia.Memorandum.Model.Memorandum;
import com.sigcqal.api.domain.ModuloCorrespondencia.Memorandum.Port.MemorandumRepositoryPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.Memorandum.Mapper.MemorandumMapper;
import com.sigcqal.api.web.ModuloCorrespondencia.Memorandum.Dto.MemorandumRequestDTO;
import com.sigcqal.api.web.ModuloCorrespondencia.Memorandum.Dto.MemorandumResponseDTO;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional(readOnly = true)
public class MemorandumService {

    @Autowired
    private MemorandumRepositoryPort repositoryPort;

   @Autowired
    private MemorandumMapper mapper; 

    @Autowired
    private  FileUploadPort fileUploadPort;

    @Autowired
    private GeneradorDocumentoService generadorDocumentoService;

  
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
        memo.setFechaEmision(LocalDateTime.now());
        memo.setIdUsuarioEncargado(request.getIdUsuarioEncargado());
        memo.setCargoEncargado(request.getCargoEncargado());
        memo.setNumeroOficio(request.getNumeroOficio());
        

       try {
    Map<String, String> variables = Map.ofEntries(
        Map.entry("{{FOLIO}}",             memo.getFolioUnico()),
        Map.entry("{{NUM_OFICIO}}",      nvl(request.getNumeroOficio(), memo.getFolioUnico())),
        Map.entry("{{ASUNTO}}",            nvl(request.getObservaciones(), "El que se indica.")),
        Map.entry("{{FECHA}}",             generadorDocumentoService.fechaActual()),
        Map.entry("{{AREA_DESTINATARIO}}", nvl(request.getAreaDestinatario(), "")),
        Map.entry("{{NOMBRE_EMISOR}}",     nvl(request.getNombreEmisor(), "")),
        Map.entry("{{INSTRUCCION}}",       nvl(request.getInstruccionSeguimiento(), "")),
        Map.entry("{{NOMBRE_FIRMANTE}}",   nvl(request.getNombreFirmante(), "")),
        Map.entry("{{AREA_FIRMANTE}}",     nvl(request.getAreaFirmante(), "")),
        Map.entry("{{USUARIO_ENCARGADO}}", nvl(request.getNombreEncargado(), "")),
        Map.entry("{{CARGO_ENCARGADO}}",   nvl(request.getCargoEncargado(), ""))
    );
 
    byte[] bytes = generadorDocumentoService
        .generarDesPlantilla("plantilla_memorandum.docx", variables);
    String url = fileUploadPort.guardarArchivo(bytes, memo.getFolioUnico() + ".docx");
    memo.setUrlSolicitudMemorandum(url);
 
} catch (Exception e) {
    System.err.println("Error generando DOCX memorándum: " + e.getMessage());
}

    return mapper.toResponse(repositoryPort.save(memo));
}

        private String nvl(String value, String fallback) {
            return (value != null && !value.isBlank()) ? value : fallback;
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

      @Transactional
    public void finalizarAsignacion(Long idMemo, byte[] archivoPdf, Long idArea) {
        Memorandum memorandum = repositoryPort.buscarPorId(idMemo)
            .orElseThrow(() -> new EntityNotFoundException("Memorándum no encontrado"));

        String nombreArchivo = "MEMO_" + idMemo + "_FIRMADO.pdf";
        String urlArchivo = fileUploadPort.guardarArchivo(archivoPdf, nombreArchivo);

        memorandum.setUrlSolicitudMemorandum(urlArchivo);
        memorandum.setIdArea(idArea);
        
        repositoryPort.save(memorandum);
    }

public List<MemorandumResponseDTO> listarAsignadosActivos(Long idArea) {
    return repositoryPort.findAsignadosActivosPorArea(idArea)
        .stream()
        .filter(m -> m != null)
        .map(mapper::toResponse)
        .collect(Collectors.toList());
}

public List<MemorandumResponseDTO> listarTodosAsignadosActivos() {
    return repositoryPort.findTodosAsignadosActivos()
        .stream().map(mapper::toResponse).collect(Collectors.toList());
}

public List<MemorandumResponseDTO> listarTodosPendientesAcuse() {
    return repositoryPort.findTodosSinAcuse()
        .stream().map(mapper::toResponse).collect(Collectors.toList());
}
}