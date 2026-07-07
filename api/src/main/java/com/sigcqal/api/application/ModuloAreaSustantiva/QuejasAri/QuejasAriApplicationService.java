package com.sigcqal.api.application.ModuloAreaSustantiva.QuejasAri;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.ModuloCorrespondencia.Documento.GeneradorDocumentoService;
import com.sigcqal.api.domain.Catalogo.Persona.Model.Persona;
import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejasAri.Model.QuejasAri;
import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejasAri.Port.QuejasAriRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ConstanciaInternaRemision.Repository.ConstanciaInternaRemisionJpaRepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Repository.QuejaJPARepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAri.Mapper.QuejasAriMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.QuejasAri.Dto.QuejasAriContextoDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.QuejasAri.Dto.QuejasAriRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.QuejasAri.Dto.QuejasAriResponseDTO;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Model.Expediente;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Queja.Model.EstatusQuejaIds;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Queja.Model.Queja;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Queja.Port.QuejaRepositoryPort;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class QuejasAriApplicationService {

    
    @Autowired
    private ConstanciaInternaRemisionJpaRepository cirJpaRepository;
    @Autowired
    private QuejaJPARepository quejaJpaRepository;
    @Autowired
    private QuejasAriRepositoryPort repositoryPort;

    @Autowired
    private QuejasAriMapper mapper;

    @Autowired
    private FileUploadPort fileUploadPort;

    @Autowired
    private GeneradorDocumentoService generadorDocumentoService;

    @Autowired
    private org.springframework.context.ApplicationContext context;

    @Autowired
private QuejaRepositoryPort quejaRepositoryPort;

    @Transactional
    public QuejasAriResponseDTO guardarQuejasAri(QuejasAriRequestDTO request) {

        QuejasAri quejasAri = new QuejasAri();
        quejasAri.setIdQueja(request.getIdQueja());
        quejasAri.setIdCir(request.getIdCir());
        //if (request.getNumExpedienteOficial() == null || request.getNumExpedienteOficial().isBlank()) {
          //  quejasAri.setNumExpedienteOficial(generarNumExpedienteOficial());
        //} else {
        //    quejasAri.setNumExpedienteOficial(request.getNumExpedienteOficial());
        //}
        if (request.getNumExpedienteOficial() == null || request.getNumExpedienteOficial().isBlank()) {
        throw new IllegalArgumentException("El número de expediente oficial es obligatorio para el registro manual.");
        }
        quejasAri.setNumExpedienteOficial(request.getNumExpedienteOficial().trim());

        quejasAri.setSintesisActosOmisiones(request.getSintesisActosOmisiones());
        quejasAri.setAbreviaturaEncargado(request.getAbreviaturaEncargado());
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
                Map.entry("{{ABREVIATURA_FIRMANTE}}", nvl(quejasAri.getAbreviaturaEncargado(), "")),
                
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
           
            if (quejasAri.getIdQueja() != null) {
    quejaJpaRepository.findIdExpedienteByIdQueja(quejasAri.getIdQueja())
        .ifPresent(idExpediente ->
            quejaJpaRepository.actualizarEstatusQueja(idExpediente, EstatusQuejaIds.ARI_GENERADO));
}

        } catch (Exception e) {
            System.err.println("Error generando DOCX quejas ARI: " + e.getMessage());
            throw new RuntimeException("No se pudo procesar el documento ARI: " + e.getMessage(), e);
        }

        return mapper.toResponse(repositoryPort.save(quejasAri));
    }

    //private String generarNumExpedienteOficial() {
    //    int year = LocalDateTime.now().getYear();
    //    String candidato;
    //    int contador = 1;
    //    do {
    //        candidato = String.format("CEDECON-ZAC-QR-%03d-%d", contador, year);
    //        contador++;
    //    } while (repositoryPort.existeNumExpediente(candidato));
    //    return candidato;
    //}

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
            .map(queja -> {
                // 1. Convertimos el modelo de dominio plano a DTO base
                QuejasAriResponseDTO responseDTO = mapper.toResponse(queja);
                
                // 2. Enriquecimiento de datos foráneos desde la Queja Padre
                try {
                    if (queja.getIdQueja() != null) {
                        // Buscamos la queja padre convirtiendo el Long a Integer (.intValue())
                        Queja quejaPadre = quejaRepositoryPort.findById(queja.getIdQueja().intValue()).orElse(null);
                        
                        if (quejaPadre != null) {
                            // 🌟 ¡MAGIA! Extraemos los campos unificados que ya existen en tu clase Queja
                            responseDTO.setFolioGobierno(quejaPadre.getFolioGobierno());
                            responseDTO.setNombreContribuyente(quejaPadre.getNombreContribuyente());
                        }
                    }
                } catch (Exception e) {
                    // Evita que un error de datos foráneos tumbe el listado completo
                    System.err.println("Advertencia al cargar datos foráneos para ARI ID " + queja.getIdAri() + ": " + e.getMessage());
                }
                
                return responseDTO;
            })
            .collect(java.util.stream.Collectors.toList());
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

public QuejasAriContextoDTO obtenerContextoPorFolio(String folio) {
    Integer idExpediente = quejaJpaRepository.findIdExpedienteByFolio(folio)
        .orElseThrow(() -> new RuntimeException("No se encontró expediente para el folio: " + folio));

    var queja = quejaJpaRepository.findByExpediente_Id(idExpediente)
        .orElseThrow(() -> new RuntimeException("No se encontró queja para el expediente: " + idExpediente));

    Long idCir = cirJpaRepository.findLatestByExpedienteId(idExpediente)
        .map(cir -> cir.getIdCir())
        .orElse(null);

    return QuejasAriContextoDTO.builder()
        .idQueja(queja.getIdQueja().longValue())
        .idCir(idCir)
        .build();
}

public record ArchivoDescarga(String nombreArchivo, byte[] contenido) {}

public Optional<ArchivoDescarga> obtenerArchivoPorFolio(String folio) {
    Integer idExpediente = quejaJpaRepository.findIdExpedienteByFolio(folio).orElse(null);
    if (idExpediente == null) return Optional.empty();

    var quejaOpt = quejaJpaRepository.findByExpediente_Id(idExpediente);
    if (quejaOpt.isEmpty()) return Optional.empty();

    Long idQueja = quejaOpt.get().getIdQueja().longValue();
    List<QuejasAri> lista = repositoryPort.findByIdQueja(idQueja);
    if (lista == null || lista.isEmpty()) return Optional.empty();

    QuejasAri ultimo = lista.stream()
        .max(Comparator.comparing(QuejasAri::getIdAri))
        .orElse(null);
    if (ultimo == null || ultimo.getRutaPdfAri() == null || ultimo.getRutaPdfAri().isBlank()) {
        return Optional.empty();
    }

    String nombreArchivo = extraerNombreArchivo(ultimo.getRutaPdfAri());
    try {
        Path filePath = Paths.get("uploads/quejas-ari/").resolve(nombreArchivo);
        byte[] contenido = Files.exists(filePath) ? Files.readAllBytes(filePath) : new byte[0];
        return Optional.of(new ArchivoDescarga(nombreArchivo, contenido));
    } catch (IOException e) {
        return Optional.empty();
    }
}

private String extraerNombreArchivo(String url) {
    if (url == null || url.isEmpty()) return "";
    return url.substring(url.lastIndexOf('/') + 1);
}
}