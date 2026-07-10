package com.sigcqal.api.application.ModuloAreaSustantiva.QuejaRlCir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sigcqal.api.application.ModuloCorrespondencia.Documento.GeneradorDocumentoService;
import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejaRlCir.Model.QuejaRlCir;
import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejaRlCir.Port.QuejaRlCirRepositoryPort;
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejaRlCir.Mapper.QuejaRlCirMapper;
import com.sigcqal.api.web.ModuloAreaSustantiva.QuejaRlCir.Dto.QuejaRlCirRequestDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.QuejaRlCir.Dto.QuejaRlCirResponseDTO;

// IMPORTS DE RELACIONES
import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Model.ResolucionFinal;
import com.sigcqal.api.domain.ModuloAreaSustantiva.ResolucionFinal.Port.ResolucionFinalRepositoryPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.ContestacionAutoridad.Port.ContestacionAutoridadPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Model.Expediente;
import com.sigcqal.api.domain.ModuloAreaSustantiva.Expediente.Port.ExpedienteRepositoryPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejasAri.Model.QuejasAri;
import com.sigcqal.api.domain.ModuloAreaSustantiva.QuejasAri.Port.QuejasAriRepositoryPort;

// CAMBIO AQUÍ: Importamos el DTO de respuesta y el Service de Expediente
import com.sigcqal.api.application.ModuloAreaSustantiva.Expediente.ExpedienteService;
import com.sigcqal.api.web.ModuloAreaSustantiva.DetalleAsesoria.Dto.DetalleAsesoriaResponseDTO;

import com.sigcqal.api.application.Catalogo.Asesor.AsesorService;
import com.sigcqal.api.web.Catalogo.Asesor.Dto.AsesorDTO;
import com.sigcqal.api.domain.ModuloAreaSustantiva.ContestacionAutoridad.Model.ContestacionAutoridad;

@Service
@Transactional(readOnly = true)
public class QuejaRlCirApplicationService {

    @Autowired
    private QuejaRlCirRepositoryPort repositoryPort;

    @Autowired
    private QuejaRlCirMapper mapper;

    @Autowired
    private FileUploadPort fileUploadPort;

    @Autowired
    private GeneradorDocumentoService generadorDocumentoService;

    @Autowired
    private ResolucionFinalRepositoryPort resolucionPort;

    @Autowired
    private ExpedienteRepositoryPort expedientePort;

    @Autowired
    private ExpedienteService expedienteService; // <- INYECTAMOS EL SERVICE EN LUGAR DE PERSONAPORT

    @Autowired
    private QuejasAriRepositoryPort quejaAriPort;

    @Autowired
    private AsesorService asesorService;

    @Autowired
    private ContestacionAutoridadPort contestacionAutoridadPort;

    @Transactional
    public QuejaRlCirResponseDTO guardarQuejaRlCir(QuejaRlCirRequestDTO request) {
        QuejaRlCir queja = new QuejaRlCir();
        queja.setIdResolucionFinal(request.getIdResolucionFinal());
        
        if (request.getFechaEmision() != null) {
            queja.setFechaEmision(request.getFechaEmision().atStartOfDay());
        } else {
            queja.setFechaEmision(LocalDateTime.now());
        }
        
        queja.setMotivos(request.getMotivos());
        queja.setArticulos(request.getArticulos());
        queja.setObservaciones(request.getObservaciones());
        queja.setOficio(request.getOficio());
        queja.setIdAsesorRemitente(request.getIdAsesorRemitente());
        queja.setIdAsesorRecibe(request.getIdAsesorRecibe());
        queja.setDirector(request.getDirector());

        try {
            ResolucionFinal resolucion = resolucionPort.findById(request.getIdResolucionFinal().intValue())
                .orElseThrow(() -> new RuntimeException("No se encontró la Resolución Final con ID: " + request.getIdResolucionFinal()));

            Expediente expediente = expedientePort.buscarPorId(resolucion.getIdExpediente().longValue())
                .orElseThrow(() -> new RuntimeException("No se encontró el Expediente vinculado"));

            QuejasAri quejaAri = quejaAriPort.buscarPorId(resolucion.getIdAri().longValue())
                .orElseThrow(() -> new RuntimeException("No se encontró el registro Queja ARI vinculado"));

            ContestacionAutoridad respuestaAutoridad = null;
            if (resolucion.getIdQuejaRespuestaAutoridad() != null) {
                respuestaAutoridad = contestacionAutoridadPort.buscarPorId(resolucion.getIdQuejaRespuestaAutoridad())
                    .orElse(null);
            }

            // 5. SOLUCIÓN OPTIMIZADA: Delegar la resolución del nombre al ExpedienteService mediante su Folio
            String nombreContribuyente = "[CONTRIBUYENTE NO ENCONTRADO]";
            if (expediente.getFolioGobierno() != null) {
                try {
                    DetalleAsesoriaResponseDTO detalleExpediente = expedienteService.obtenerDetalleCompletoPorFolio(expediente.getFolioGobierno());
                    if (detalleExpediente != null && detalleExpediente.getContribuyente() != null) {
                        nombreContribuyente = detalleExpediente.getContribuyente();
                    }
                } catch (Exception e) {
                    System.err.println("No se pudo obtener el detalle del expediente para el folio " + expediente.getFolioGobierno() + ": " + e.getMessage());
                }
            }

            AsesorDTO remitenteDto = asesorService.findById(request.getIdAsesorRemitente());
            AsesorDTO recibeDto = asesorService.findById(request.getIdAsesorRecibe());

            String nombreRemitente = remitenteDto.getNombreCompleto();
            String nombreRecibe = recibeDto.getNombreCompleto();

            Map<String, String> variables = Map.ofEntries(
                Map.entry("{{FECHA_EMISION}}", generadorDocumentoService.fechaActual()),
                Map.entry("{{MOTIVOS}}", nvl(queja.getMotivos(), "")),
                Map.entry("{{ARTICULOS}}", nvl(queja.getArticulos(), "")),
                Map.entry("{{OBSERVACIONES}}", nvl(queja.getObservaciones(), "")),
                Map.entry("{{OFICIO}}", nvl(queja.getOficio(), "")),
                Map.entry("{{DIRECTOR}}", nvl(queja.getDirector(), "")),
                
                Map.entry("{{FOLIO_GOBIERNO}}", nvl(expediente.getFolioGobierno(), "[FOLIO NULO]")),
                Map.entry("{{CONTRIBUYENTE}}", nvl(nombreContribuyente, "[CONTRIBUYENTE NULO]")),
                Map.entry("{{NUMERO_OFICIO}}", nvl(respuestaAutoridad != null ? respuestaAutoridad.getNumeroOficio() : null, "[NÚM OFICIO NULO]")),
                Map.entry("{{MULTAS_CREDITO}}", nvl(quejaAri.getMultasCredito(), "0.00")),
                Map.entry("{{NUM_EXPEDIENTE_OFICIAL}}", nvl(quejaAri.getNumExpedienteOficial(), "[EXPEDIENTE NULO]")),
                
                Map.entry("{{ASESOR_REMITENTE}}", nvl(nombreRemitente, "[REMITENTE NULO]")),
                Map.entry("{{ASESOR_RECIBE}}", nvl(nombreRecibe, "[RECIBE NULO]"))
            );

            byte[] bytes = generadorDocumentoService.generarDesPlantilla("plantilla_queja_rl_cir.docx", variables);
            
            String nombreArchivoBase = "QUEJA_RL_CIR_RES_" + queja.getIdResolucionFinal();
            String url = fileUploadPort.guardarArchivoQuejaRlCir(bytes, nombreArchivoBase + ".docx");
            
            if (url == null || url.isBlank()) {
                throw new RuntimeException("El servicio de almacenamiento devolvió una ruta nula o vacía.");
            }

            queja.setRutaPdfQuejaRlCir(url);

        } catch (Exception e) {
            System.err.println("Error procesando Enfoque B para Queja RLCir: " + e.getMessage());
            throw new RuntimeException("No se pudo procesar el documento Queja RLCir: " + e.getMessage(), e);
        }

        return mapper.toResponse(repositoryPort.save(queja));
    }

    public List<QuejaRlCirResponseDTO> listarQuejaRlCir() {
        List<QuejaRlCir> lista = repositoryPort.findAll();
        if (lista == null) return List.of();

        return lista.stream()
            .filter(item -> item != null)
            .map(mapper::toResponse)
            .collect(Collectors.toList());
    }

    private String nvl(String valor, String defecto) {
        return (valor == null || valor.isBlank()) ? defecto : valor;
    }

    public record ArchivoDescarga(String nombreArchivo, byte[] contenido) {}

    public Optional<ArchivoDescarga> obtenerArchivoPorId(Long idQuejaRlCir) {
        Optional<QuejaRlCir> quejaOpt = repositoryPort.buscarPorId(idQuejaRlCir);
        if (quejaOpt.isEmpty()) return Optional.empty();

        QuejaRlCir queja = quejaOpt.get();
        if (queja.getRutaPdfQuejaRlCir() == null || queja.getRutaPdfQuejaRlCir().isBlank()) {
            return Optional.empty();
        }

        String nombreArchivo = queja.getRutaPdfQuejaRlCir().substring(queja.getRutaPdfQuejaRlCir().lastIndexOf('/') + 1);
        try {
            Path filePath = Paths.get("uploads/queja-rl-cir").resolve(nombreArchivo);
            byte[] contenido = Files.exists(filePath) ? Files.readAllBytes(filePath) : new byte[0];
            return Optional.of(new ArchivoDescarga(nombreArchivo, contenido));
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}