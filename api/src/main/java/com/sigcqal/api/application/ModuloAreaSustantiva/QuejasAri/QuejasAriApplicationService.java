package com.sigcqal.api.application.ModuloAreaSustantiva.QuejasAri;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.ModuloCorrespondencia.Documento.GeneradorDocumentoService;
import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejasAri.Model.QuejasAri;
import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejasAri.Port.QuejasAriRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAri.Mapper.QuejasAriMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.QuejasAri.Dto.QuejasAriRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.QuejasAri.Dto.QuejasAriResponseDTO;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class QuejasAriApplicationService {

    @Autowired
    private QuejasAriRepositoryPort repositoryPort;

    @Autowired
    private QuejasAriMapper mapper;

    @Autowired
    private FileUploadPort fileUploadPort;

    @Autowired
    private GeneradorDocumentoService generadorDocumentoService;

    @Transactional
    public QuejasAriResponseDTO guardarQuejasAri(QuejasAriRequestDTO request) {

        QuejasAri quejasAri = new QuejasAri();
        quejasAri.setIdQueja(request.getIdQueja());
        quejasAri.setIdCir(request.getIdCir());
        if (request.getNumExpedienteOficial() == null || request.getNumExpedienteOficial().isBlank()) {
            quejasAri.setNumExpedienteOficial(generarNumExpedienteOficial());
        } else {
            quejasAri.setNumExpedienteOficial(request.getNumExpedienteOficial());
        }
        quejasAri.setSintesisActosOmisiones(request.getSintesisActosOmisiones());
        quejasAri.setNombreEncargadoFirma(request.getNombreEncargadoFirma());
        quejasAri.setFechaAcuerdo(request.getFechaAcuerdo());
        quejasAri.setIdPlantillaQuejaAri(request.getIdPlantillaQuejaAri());
        quejasAri.setMultasRequerimientos(request.getMultasRequerimientos());
        quejasAri.setInstituto(request.getInstituto());
        quejasAri.setMultasCredito(request.getMultasCredito());
        quejasAri.setRutaPdfAri(request.getRutaPdfAri());
        

        try {
            Map<String, String> variables = Map.of(
                "{{NUM_EXPEDIENTE}}", quejasAri.getNumExpedienteOficial() != null ? quejasAri.getNumExpedienteOficial() : "Por definir",
                "{{FECHA}}", generadorDocumentoService.fechaActual(),
                "{{SINTESIS_ACTOS}}", nvl(quejasAri.getSintesisActosOmisiones(), ""),
                "{{NOMBRE_FIRMANTE}}", nvl(quejasAri.getNombreEncargadoFirma(), ""),
                "{{MULTAS_REQUERIMIENTOS}}", nvl(quejasAri.getMultasRequerimientos(), ""),
                "{{MULTAS_CREDITO}}", nvl(quejasAri.getMultasCredito(), ""),
                "{{INSTITUTO}}", nvl(quejasAri.getInstituto(), "")
            );

            byte[] bytes = generadorDocumentoService
                .generarDesPlantilla("plantilla_queja_ari.docx", variables);
            
            String url = fileUploadPort.guardarArchivoQuejaAri(bytes, quejasAri.getNumExpedienteOficial() + ".docx");
            
            if (url == null || url.isBlank()) {
                throw new RuntimeException("El servicio de almacenamiento (FileUploadPort) devolvió una ruta nula o vacía.");
            }

            quejasAri.setRutaPdfAri(url);

        } catch (Exception e) {
            System.err.println("Error generando DOCX quejas ARI: " + e.getMessage());
            throw new RuntimeException("No se pudo procesar el documento ARI: " + e.getMessage(), e);
        }

        return mapper.toResponse(repositoryPort.save(quejasAri));
    }

    private String generarNumExpedienteOficial() {
        int year = LocalDateTime.now().getYear();
        String candidato;
        int contador = 1;
        do {
            candidato = String.format("CEDECON-ZAC-QR-%03d-%d", contador, year);
            contador++;
        } while (repositoryPort.existeNumExpediente(candidato));
        return candidato;
    }

    private String nvl(String value, String fallback) {
        return (value != null && !value.isBlank()) ? value : fallback;
    }

    public List<QuejasAriResponseDTO> listarQuejasAri() {
        List<QuejasAri> quejasAriList = repositoryPort.findAll();
        
        if (quejasAriList == null) {
            return List.of();
        }

        return quejasAriList.stream()
            .filter(queja -> queja != null)
            .map(mapper::toResponse)
            .collect(Collectors.toList());
    }

    public List<QuejasAriResponseDTO> listarPorIdQueja(Long idQueja) {
        List<QuejasAri> quejasAriList = repositoryPort.findByIdQueja(idQueja);
        
        if (quejasAriList == null) {
            return List.of();
        }

        return quejasAriList.stream()
            .filter(queja -> queja != null)
            .map(mapper::toResponse)
            .collect(Collectors.toList());
    }

    public QuejasAriResponseDTO buscarPorId(Long id) {
        return repositoryPort.buscarPorId(id)
            .map(mapper::toResponse)
            .orElseThrow(() -> new RuntimeException("No se encontró QuejasAri con ID: " + id));
    }

    @Transactional
    public void finalizarAri(Long idAri, byte[] archivoPdf) {
        QuejasAri quejasAri = repositoryPort.buscarPorId(idAri)
            .orElseThrow(() -> new EntityNotFoundException("QuejasAri no encontrado"));

        String nombreArchivo = "ARI_" + idAri + "_FIRMADO.pdf";
        String urlArchivo = fileUploadPort.guardarArchivo(archivoPdf, nombreArchivo);

        quejasAri.setRutaPdfAri(urlArchivo);
        
        repositoryPort.save(quejasAri);
    }
}