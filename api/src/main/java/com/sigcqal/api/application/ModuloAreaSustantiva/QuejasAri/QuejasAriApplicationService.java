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

    import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
        quejasAri.setNombreEncargadoFirma(request.getNombreEncargadoFirma());if (request.getFechaAcuerdo() != null) {
        quejasAri.setFechaAcuerdo(request.getFechaAcuerdo().atStartOfDay());
        }
        quejasAri.setIdPlantillaQuejaAri(request.getIdPlantillaQuejaAri());
        quejasAri.setMultasRequerimientos(request.getMultasRequerimientos());
        quejasAri.setInstituto(request.getInstituto());
        quejasAri.setMultasCredito(request.getMultasCredito());
        quejasAri.setRutaPdfAri(request.getRutaPdfAri());

        try {
            Map<String, String> variables = Map.ofEntries(
                // Datos del expediente e institucionales (propios de ARI)
                Map.entry("{{NUM_EXPEDIENTE}}",       nvl(quejasAri.getNumExpedienteOficial(), "Por definir")),
                Map.entry("{{FECHA}}",                generadorDocumentoService.fechaActual()),
                Map.entry("{{SINTESIS_ACTOS}}",       nvl(quejasAri.getSintesisActosOmisiones(), "")),
                Map.entry("{{NOMBRE_FIRMANTE}}",      nvl(quejasAri.getNombreEncargadoFirma(), "")),
                Map.entry("{{MULTAS_REQUERIMIENTOS}}", nvl(quejasAri.getMultasRequerimientos(), "")),
                Map.entry("{{MULTAS_CREDITO}}",       nvl(quejasAri.getMultasCredito(), "")),
                Map.entry("{{INSTITUTO}}",             nvl(quejasAri.getInstituto(), "")),
                
                // 🔍 Nuevas variables inyectadas desde el Front-End para la plantilla .docx
                Map.entry("{{FOLIO_GOBIERNO}}",       nvl(request.getFolioGobierno(), "[FOLIO]")),
                Map.entry("{{NOMBRE_ASESOR}}",        nvl(request.getNombreAsesor(), "[ASESOR]")),
                Map.entry("{{RFC_ASESOR}}",           nvl(request.getRfcAsesor(), "[RFC ASESOR]")),
                Map.entry("{{NOMBRE_REPRESENTANTE}}", nvl(request.getNombreRepresentante(), "")),
                Map.entry("{{NOMBRE_CONTRIBUYENTE}}",        nvl(request.getNombreContribuyente(), "[CONTRIBUYENTE]")),
                Map.entry("{{IDENTIFICACION_CONTRIBUYENTE}}", nvl(request.getIdentificacionContribuyente(), "")),
                Map.entry("{{FECHA_SOLICITUD}}",      formatearFechaEspanol(request.getFechaSolicitud()))
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



private String formatearFechaEspanol(String fechaInput) {
    if (fechaInput == null || fechaInput.isBlank() || fechaInput.contains("{")) {
        return "[FECHA SOLICITUD]";
    }
    try {
        // Por si viene con la hora (2026-06-01 10:00:00.000 o con 'T'), tomamos solo la parte de la fecha YYYY-MM-DD
        String fechaLimpia = fechaInput.split("[ T]")[0]; 
        
        LocalDate fecha = LocalDate.parse(fechaLimpia);
        
        // Creamos el formateador con la estructura deseada y el idioma correcto
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", new Locale("es", "MX"));
        
        return fecha.format(formateador); // Retornará por ejemplo: "8 de junio de 2026"
    } catch (Exception e) {
        // Si por alguna razón falla el parseo (formato inesperado), regresamos el valor original para no romper el flujo
        System.err.println("Error al formatear fechaSolicitud en el Back-End: " + e.getMessage());
        return fechaInput;
    }
}
}