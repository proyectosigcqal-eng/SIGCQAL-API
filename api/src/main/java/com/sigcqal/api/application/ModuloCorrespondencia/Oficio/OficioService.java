package com.sigcqal.api.application.ModuloCorrespondencia.Oficio;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.ModuloCorrespondencia.Documento.GeneradorDocumentoService;
import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.domain.ModuloCorrespondencia.Oficio.Model.Oficio;
import com.sigcqal.api.domain.ModuloCorrespondencia.Oficio.Port.OficioRepositoryPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.Oficio.Mapper.OficioMapper;
import com.sigcqal.api.web.ModuloCorrespondencia.Oficio.Dto.OficioRequestDTO;
import com.sigcqal.api.web.ModuloCorrespondencia.Oficio.Dto.OficioResponseDTO;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class OficioService {

    @Autowired
    private OficioRepositoryPort repositoryPort;

   @Autowired
    private OficioMapper mapper; 

    @Autowired
    private  FileUploadPort fileUploadPort;

    @Autowired
private GeneradorDocumentoService generadorDocumentoService;

  
    @Transactional
    public OficioResponseDTO guardarOficio(OficioRequestDTO request) {

        Oficio oficio = new Oficio();
        oficio.setIdCorrespondencia(request.getIdCorrespondencia());
        oficio.setIdPlantilla(request.getIdPlantilla());
        oficio.setIdUsuarioEmisor(request.getIdUsuarioEmisor());
        oficio.setIdUsuarioFirmante(request.getIdUsuarioFirmante());
        oficio.setInstruccionSeguimiento(request.getInstruccionSeguimiento());
        oficio.setObservaciones(request.getObservaciones());
        oficio.setFolioUnico(generarFolioSeguro());
        oficio.setUrlSolicitudMemorandum(request.getUrlSolicitudMemorandum());
        oficio.setIdArea(request.getIdArea());
        
try {
    Map<String, String> variables = Map.of(
        "{{FOLIO}}",              oficio.getFolioUnico(),
        "{{ASUNTO}}",             nvl(request.getObservaciones(), "El que se indica."),
        "{{FECHA}}",              generadorDocumentoService.fechaActual(),
        "{{AREA_DESTINATARIO}}", nvl(request.getAreaDestinatario(), ""),
        "{{NOMBRE_EMISOR}}",      nvl(request.getNombreEmisor(), null),
        "{{INSTRUCCION}}",        nvl(request.getInstruccionSeguimiento(), ""),
        "{{NOMBRE_FIRMANTE}}",    nvl(request.getNombreFirmante(), ""),
        "{{AREA_FIRMANTE}}",      nvl(request.getAreaFirmante(), "")
    );

    byte[] bytes = generadorDocumentoService
        .generarDesPlantilla("plantilla_oficio.docx", variables);  // ← solo cambia la plantilla
    String url = fileUploadPort.guardarArchivo(bytes, oficio.getFolioUnico() + ".docx");
    oficio.setUrlSolicitudMemorandum(url);

} catch (Exception e) {
    System.err.println("Error generando DOCX oficio: " + e.getMessage());
}

return mapper.toResponse(repositoryPort.save(oficio));
    }

       private String nvl(String value, String fallback) {
            return (value != null && !value.isBlank()) ? value : fallback;
        }

    public List<OficioResponseDTO> listarOficio() {
        List<Oficio> oficios = repositoryPort.findAll();
        
        if (oficios == null) {
            return List.of(); 
        }


        return oficios.stream()
            .filter(ofi -> ofi != null) 
            .map(mapper::toResponse) 
            .collect(Collectors.toList());
    }

public List<OficioResponseDTO> listarPorArea(Long idArea) {
    List<Oficio> oficios = repositoryPort.findByArea(idArea);
    
    if (oficios == null) {
        return List.of();
    }

    return oficios.stream()
        .filter(ofi -> ofi != null)
        .map(mapper::toResponse)
        .collect(Collectors.toList());
}

public OficioResponseDTO buscarPorId(Long id) {
    return repositoryPort.buscarPorId(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new RuntimeException("No se encontró el oficio con ID: " + id));
}

    private String generarFolioSeguro() {
        String nuevoFolio;
        do {
            nuevoFolio = "OFICIO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (repositoryPort.existeFolio(nuevoFolio)); 
        return nuevoFolio;
    }

      @Transactional
    public void finalizarAsignacion(Long idOficio, byte[] archivoPdf, Long idArea) {
        Oficio oficio = repositoryPort.buscarPorId(idOficio)
            .orElseThrow(() -> new EntityNotFoundException("Oficio no encontrado"));

        String nombreArchivo = "OFICIO_" + idOficio + "_FIRMADO.pdf";
        String urlArchivo = fileUploadPort.guardarArchivo(archivoPdf, nombreArchivo);

        oficio.setUrlSolicitudMemorandum(urlArchivo);
        oficio.setIdArea(idArea);
        
        repositoryPort.save(oficio);
    }


}
