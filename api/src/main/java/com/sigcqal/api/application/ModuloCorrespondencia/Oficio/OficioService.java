package com.sigcqal.api.application.ModuloCorrespondencia.Oficio;

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
import com.sigcqal.api.domain.ModuloCorrespondencia.AcuseOficio.Port.AcuseOficioRepositoryPort;
import com.sigcqal.api.domain.ModuloCorrespondencia.Oficio.Model.Oficio;
import com.sigcqal.api.domain.ModuloCorrespondencia.Oficio.Port.OficioRepositoryPort;
import com.sigcqal.api.infra.ModuloCorrespondencia.Oficio.Mapper.OficioMapper;
import com.sigcqal.api.web.ModuloCorrespondencia.Oficio.Dto.OficioRequestDTO;
import com.sigcqal.api.web.ModuloCorrespondencia.Oficio.Dto.OficioResponseDTO;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional(readOnly = true)
public class OficioService {

    @Autowired
    private OficioRepositoryPort repositoryPort;

   @Autowired
    private OficioMapper mapper; 

    @Autowired
    private  FileUploadPort fileUploadPort;

    @Autowired
private GeneradorDocumentoService generadorDocumentoService;

@Autowired
    private AcuseOficioRepositoryPort acuseOficioRepositoryPort;

  
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
        oficio.setFechaEmision(LocalDateTime.now());
        
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

public List<OficioResponseDTO> listarPendientesAcuse(Long idArea) {
    // Usamos el nuevo método del repositorio que filtra los que no tienen acuse
    List<Oficio> oficios = repositoryPort.findSinAcuseByArea(idArea);
    
    if (oficios == null) {
        return List.of();
    }

    return oficios.stream()
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
        // 1. Buscamos el oficio
        Oficio oficio = repositoryPort.buscarPorId(idOficio)
            .orElseThrow(() -> new EntityNotFoundException("Oficio no encontrado"));

        // 2. Guardamos el PDF
        String nombreArchivo = "OFICIO_" + idOficio + "_FIRMADO.pdf";
        String urlArchivo = fileUploadPort.guardarArchivo(archivoPdf, nombreArchivo);

        // 3. Actualizamos el oficio
        oficio.setUrlSolicitudMemorandum(urlArchivo);
        oficio.setIdArea(idArea);
        
        repositoryPort.save(oficio);

        // 4. NUEVO: Creamos el Acuse de Oficio pendiente de revisión
        // (Asegúrate de importar tu clase de dominio AcuseOficio)
        com.sigcqal.api.domain.ModuloCorrespondencia.AcuseOficio.Model.AcuseOficio acuse = 
            new com.sigcqal.api.domain.ModuloCorrespondencia.AcuseOficio.Model.AcuseOficio();
        
        acuse.setIdOficio(idOficio);
        acuse.setEsDelArea(true); // FUNDAMENTAL para que tu query del frontend lo encuentre
        
        // Nota: NO seteamos usuarioRevisor, fechaAceptacion ni horaAceptacion 
        // porque apenas está "pendiente". Se llenarán cuando alguien lo revise.

        // 5. Guardamos el acuse en la base de datos
        acuseOficioRepositoryPort.save(acuse); 
    }

}
