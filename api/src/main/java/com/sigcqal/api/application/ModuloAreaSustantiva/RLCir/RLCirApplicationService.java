package com.sigcqal.api.application.ModuloAreaSustantiva.RLCir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.sigcqal.api.application.ModuloCorrespondencia.Documento.GeneradorDocumentoService;
import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.RLCir.Model.RLCir;
import com.sigcqal.api.domain.ModuloAreaSustantiva.RLCir.Port.RLCirRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.RLCir.Mapper.RLCirMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.RLCir.Dto.RLCirRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.RLCir.Dto.RLCirResponseDTO;

@Service
public class RLCirApplicationService {

    @Autowired
    private RLCirRepositoryPort repositoryPort;

    @Autowired
    private RLCirMapper mapper;

    @Autowired
    private FileUploadPort fileUploadPort;

    @Autowired
    private GeneradorDocumentoService generadorDocumentoService;

    @Transactional
    public RLCirResponseDTO guardarRLCir(RLCirRequestDTO request) {
        RLCir rlCir = new RLCir();
        rlCir.setIdExpediente(request.getIdExpediente());
        
        if (request.getFechaEmision() != null) {
            rlCir.setFechaEmision(request.getFechaEmision().atStartOfDay());
        } else {
            rlCir.setFechaEmision(LocalDateTime.now());
        }
        
        rlCir.setMotivos(request.getMotivos());
        rlCir.setArticulos(request.getArticulos());
        rlCir.setObservaciones(request.getObservaciones());
        rlCir.setIdAsesorRemitente(request.getIdAsesorRemitente());
        rlCir.setIdAsesorRecibe(request.getIdAsesorRecibe());
        rlCir.setDirector(request.getDirector());

        try {
            // Mapeo seguro de variables para la inyección del documento .docx
            Map<String, String> variables = Map.ofEntries(
                Map.entry("{{FECHA}}", generadorDocumentoService.fechaActual()),
                Map.entry("{{MOTIVOS}}", nvl(rlCir.getMotivos(), "")),
                Map.entry("{{ARTICULOS}}", nvl(rlCir.getArticulos(), "")),
                Map.entry("{{OBSERVACIONES}}", nvl(rlCir.getObservaciones(), "")),
                Map.entry("{{DIRECTOR}}", nvl(rlCir.getDirector(), "")),
                Map.entry("{{FOLIO}}", nvl(request.getFolioGobierno(), "[FOLIO NULO]")),
                Map.entry("{{CONTRIBUYENTE}}", nvl(request.getNombreContribuyente(), "[CONTRIBUYENTE]")),
                Map.entry("{{REMITENTE}}", nvl(request.getNombreAsesorRemitente(), "[REMITENTE]")),
                Map.entry("{{RECIBE}}", nvl(request.getNombreAsesorRecibe(), "[RECIBE]")),
                Map.entry("{{IDENTIFICACION}}", nvl(request.getIdentificacionOficial(), "[IDENTIFICACIÓN NULA]"))
            );

            // Supongamos que tu plantilla base se llama plantilla_rl_cir.docx
            byte[] bytes = generadorDocumentoService.generarDesPlantilla("plantilla_rl_cir.docx", variables);
            
            // Subir usando una nomenclatura lógica usando el ID del expediente como identificador base
            String nombreArchivoBase = "RL_CIR_EXP_" + (rlCir.getIdExpediente() != null ? rlCir.getIdExpediente() : "NUEVO");
            String url = fileUploadPort.guardarArchivoRLCir(bytes, nombreArchivoBase + ".docx");
            
            if (url == null || url.isBlank()) {
                throw new RuntimeException("El servicio de almacenamiento devolvió una ruta nula o vacía.");
            }

            rlCir.setRutaPdfRlCir(url);

        } catch (Exception e) {
            System.err.println("Error generando DOCX para RLCir: " + e.getMessage());
            throw new RuntimeException("No se pudo procesar el documento RLCir: " + e.getMessage(), e);
        }

        return mapper.toResponse(repositoryPort.save(rlCir));
    }

    public List<RLCirResponseDTO> listarRLCir() {
        List<RLCir> lista = repositoryPort.findAll();
        if (lista == null) return List.of();

        return lista.stream()
            .filter(item -> item != null)
            .map(item -> {
                RLCirResponseDTO responseDTO = mapper.toResponse(item);
                // Aquí puedes enriquecer campos dinámicos de ser necesario tal como se hizo en Ari
                return responseDTO;
            })
            .collect(Collectors.toList());
    }

    private String nvl(String valor, String defecto) {
        return (valor == null || valor.isBlank()) ? defecto : valor;
    }

    public record ArchivoDescarga(String nombreArchivo, byte[] contenido) {}

    public Optional<ArchivoDescarga> obtenerArchivoPorId(Long idRlCir) {
        Optional<RLCir> rlCirOpt = repositoryPort.buscarPorId(idRlCir);
        if (rlCirOpt.isEmpty()) return Optional.empty();

        RLCir rlCir = rlCirOpt.get();
        if (rlCir.getRutaPdfRlCir() == null || rlCir.getRutaPdfRlCir().isBlank()) {
            return Optional.empty();
        }

        String nombreArchivo = rlCir.getRutaPdfRlCir().substring(rlCir.getRutaPdfRlCir().lastIndexOf('/') + 1);
        try {
            // Ajustado al directorio correspondiente de almacenamiento temporal local de cargas
            Path filePath = Paths.get("uploads/RLCir").resolve(nombreArchivo);
            byte[] contenido = Files.exists(filePath) ? Files.readAllBytes(filePath) : new byte[0];
            return Optional.of(new ArchivoDescarga(nombreArchivo, contenido));
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}