package com.sigcqal.api.application.ModuloAreaSustantiva.ConstanciaInternaRemision;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.ModuloCorrespondencia.Documento.GeneradorDocumentoService;
import com.sigcqal.api.application.exception.DuplicateResourceException;
import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.DetalleAsesoria.Model.DetalleAsesoria;
import com.sigcqal.api.infra.Catalogo.DetalleAsesoria.Entity.DetalleAsesoriaEntity;
import com.sigcqal.api.infra.Catalogo.DetalleAsesoria.Repository.DetalleAsesoriaJpaRepository;
import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Entity.EstatusExpedienteEntity;
import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Repository.EstatusExpedienteJpaRepository;
import com.sigcqal.api.infra.Catalogo.Persona.Entity.PersonaEntity;
import com.sigcqal.api.infra.Catalogo.TipoTramite.Entity.TipoTramiteEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ConstanciaInternaRemision.Entity.ConstanciaInternaRemisionEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.ConstanciaInternaRemision.Repository.ConstanciaInternaRemisionJpaRepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.DetalleAsesoria.Repository.DetalleAsesoriaRepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Repository.ExpedienteJPARepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Entity.QuejaEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Repository.QuejaJPARepository;
import com.sigcqal.api.web.ModuloAreaSustantiva.ConstanciaInternaRemision.Dto.ConstanciaPreviewResponse;
import com.sigcqal.api.web.ModuloAreaSustantiva.ConstanciaInternaRemision.Dto.GenerarConstanciaRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConstanciaInternaRemisionService {

    private static final Locale LOCALE_ES_MX = new Locale("es", "MX");
    private static final DateTimeFormatter FMT_FECHA_DOCUMENTO = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy",
            LOCALE_ES_MX);
    private static final DateTimeFormatter FMT_FECHA_CORTA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm",
            LOCALE_ES_MX);
    private static final String ESTATUS_CONSTANCIA_EMITIDA = "Constancia Emitida";
    private static final String FALLBACK_ASESOR = "ASESOR NO DISPONIBLE";
    private static final String PLANTILLA_CONSTANCIA = "plantilla_constancia_interna_remision.docx";
    private static final String PLANTILLA_CIR_PREVIEW = "cir_preview.html";
    private static final String AREA_CANALIZAR = "Representación Legal y Defensa";
    private static final String SERVICIO_PREVIO = "Quejas";

    // ============ Repositories ============
    private final DetalleAsesoriaRepository detalleRepository;
    private final DetalleAsesoriaJpaRepository detalleAsesoriaJpaRepository; // ✅ CORREGIDO
    private final ExpedienteJPARepository expedienteRepository;
    private final EstatusExpedienteJpaRepository estatusRepository;
    private final ConstanciaInternaRemisionJpaRepository cirRepository;
    private final QuejaJPARepository quejaRepository;

    // ============ Ports & Services ============
    private final FileUploadPort fileUploadPort;
    private final GeneradorDocumentoService generadorDocumentoService;

    // ============ PREVIEW ============

    public String generarHtmlPreview(Integer expedienteId, GenerarConstanciaRequest request) {
        validarExpedienteId(expedienteId);
        DatosConstancia datos = cargarDatosConstancia(expedienteId);
        String html = loadTemplate(PLANTILLA_CIR_PREVIEW);
        String membreteBase64 = loadMembreteBase64();

        html = html
                .replace("{{MEMBRETE_BASE64}}", membreteBase64)
                .replace("{{FOLIO_EXPEDIENTE}}", sanitize(datos.folioExpediente()))
                .replace("{{CONTRIBUYENTE}}", sanitize(resolverNombreContribuyente(datos)))
                .replace("{{AUTORIDAD_RESPONSABLE}}", sanitize(datos.autoridadResponsable()))
                .replace("{{ASUNTO}}", sanitize(datos.asunto()))
                .replace("{{FECHA_EMISION}}", datos.fechaGeneracion().format(FMT_FECHA_DOCUMENTO))
                .replace("{{NUMERO_CONSTANCIA}}", sanitize(datos.numeroConstancia()))
                .replace("{{DOCUMENTACION_REMITE}}", sanitize(request.getDocumentacionRemite()))
                .replace("{{DOCUMENTACION_CLASS}}",
                        isNullOrEmpty(request.getDocumentacionRemite()) ? "content-empty" : "")
                .replace("{{MOTIVOS_REMITE}}", sanitize(request.getMotivosRemite()))
                .replace("{{MOTIVOS_CLASS}}", isNullOrEmpty(request.getMotivosRemite()) ? "content-empty" : "")
                .replace("{{OBSERVACIONES}}", sanitize(request.getObservaciones()))
                .replace("{{OBSERVACIONES_CLASS}}", isNullOrEmpty(request.getObservaciones()) ? "content-empty" : "")
                .replace("{{ASESOR_QUE_REMITE}}", sanitize(request.getAsesorQueRemite()))
                .replace("{{NOMBRE_ENCARGADO}}", sanitize(request.getNombreEncargado()))
                .replace("{{FECHA_GENERACION}}", LocalDateTime.now().format(FMT_FECHA_CORTA))
                .replace("{{ASESOR_EMISOR}}", sanitize(datos.asesorEmisor()));

        return html;
    }

    // ============ GENERACIÓN ============

    public ConstanciaPreviewResponse obtenerPreview(String folio) {
        var expedienteOpt = expedienteRepository.findByFolioGobierno(folio);
        if (expedienteOpt.isEmpty()) {
            throw new InvalidRequestException("Expediente no encontrado con folio: " + folio);
        }
        Integer expedienteId = expedienteOpt.get().getId();
        DatosConstancia datos = cargarDatosConstancia(expedienteId);
        String motivoBloqueo = obtenerMotivoBloqueo(datos);

        return ConstanciaPreviewResponse.builder()
                .expedienteId(datos.expedienteId())
                .folioAsesoria(datos.folioAsesoria())
                .nombreQuejoso(datos.nombreQuejoso())
                .asunto(datos.asunto())
                .autoridadResponsable(datos.autoridadResponsable())
                .fechaGeneracion(
                        datos.fechaGeneracion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", LOCALE_ES_MX)))
                .numeroConstancia(datos.numeroConstancia())
                .asesorEmisor(datos.asesorEmisor())
                .puedeGenerar(motivoBloqueo == null)
                .motivoBloqueo(motivoBloqueo)
                .build();
    }

    @Transactional
    public ConstanciaGenerada generar(Integer expedienteId, GenerarConstanciaRequest request) {
        validarExpedienteId(expedienteId);
        validarRequest(request);

        DatosConstancia datos = cargarDatosConstancia(expedienteId);
        String motivoBloqueo = obtenerMotivoBloqueo(datos);
        if (motivoBloqueo != null) {
            if (motivoBloqueo.startsWith("Ya existe")) {
                throw new DuplicateResourceException(motivoBloqueo);
            }
            throw new InvalidRequestException(motivoBloqueo);
        }

        byte[] archivo = generarDocx(construirVariablesTemplate(datos, request));
        String url = fileUploadPort.guardarArchivoConstancia(archivo, datos.nombreArchivo());

        // EstatusExpedienteEntity estatusConstancia = estatusRepository.findByNombreIgnoreCase(ESTATUS_CONSTANCIA_EMITIDA)
        //         .orElseThrow(() -> new InvalidRequestException(
        //                 "No se encontró el estatus de expediente requerido: " + ESTATUS_CONSTANCIA_EMITIDA));

        //datos.expediente().setEstatusExpediente(estatusConstancia);
        expedienteRepository.save(datos.expediente());

        ConstanciaInternaRemisionEntity cir = ConstanciaInternaRemisionEntity.builder()
                .queja(datos.queja())
                .expediente(datos.expediente())
                .fechaEmision(datos.fechaGeneracion())
                .rutaPdfCir(url)
                .documentacionRemite(request.getDocumentacionRemite())
                .motivosRemite(request.getMotivosRemite())
                .observaciones(request.getObservaciones())
                .asesorQueRemite(request.getAsesorQueRemite())
                .fundamentos(request.getFundamentos())
                .asesorEjecutor(
                        datos.expediente().getAsesor())
                .areaRecibe(null)
                .areaRemite(null)
                .tipoTramite(datos.tipoTramite())
                .build();

        cirRepository.save(cir);

        log.info("Constancia generada: expedienteId={} quejaId={} usuario={} cirId={}",
                datos.expedienteId(),
                datos.queja() != null ? datos.queja().getIdQueja() : "null",
                datos.asesorEmisor(),
                cir.getIdCir());

        return new ConstanciaGenerada(datos.nombreArchivo(), url, archivo);
    }

    public Optional<ConstanciaGenerada> obtenerExistente(String folio) {
        var expedienteOpt = expedienteRepository.findByFolioGobierno(folio);
        if (expedienteOpt.isEmpty() || expedienteOpt.get().getId() == null) {
            return Optional.empty();
        }

        Optional<ConstanciaInternaRemisionEntity> cirOpt = cirRepository
                .findLatestByExpedienteId(expedienteOpt.get().getId());

        if (cirOpt.isEmpty() || cirOpt.get().getRutaPdfCir() == null) {
            return Optional.empty();
        }

        try {
            ConstanciaInternaRemisionEntity cir = cirOpt.get();
            String url = cir.getRutaPdfCir();
            String nombreArchivo = extractNombreArchivoFromUrl(url);
            Path filePath = Paths.get("uploads/constancias/").resolve(nombreArchivo);
            byte[] contenido = Files.exists(filePath) ? Files.readAllBytes(filePath) : new byte[0];
            return Optional.of(new ConstanciaGenerada(nombreArchivo, url, contenido));
        } catch (Exception e) {
            log.error("Error al obtener CIR existente", e);
            return Optional.empty();
        }
    }

    // ============ HELPERS ============

    private String loadMembreteBase64() {
        try {
            ClassPathResource resource = new ClassPathResource("assets/membrete.jpg");
            if (!resource.exists()) {
                log.warn("Membrete no encontrado");
                return "";
            }
            byte[] bytes = resource.getInputStream().readAllBytes();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            log.error("Error al cargar membrete", e);
            return "";
        }
    }

    private String loadTemplate(String templateName) {
        try {
            ClassPathResource resource = new ClassPathResource("plantillas/" + templateName);
            if (!resource.exists()) {
                throw new InvalidRequestException("Template no encontrado: " + templateName);
            }
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error cargando template", e);
        }
    }

    private String sanitize(String input) {
        if (input == null)
            return "";
        return input
                .replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\r\n|\r|\n", "<br/>");
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private byte[] generarDocx(Map<String, String> variables) {
        try {
            return generadorDocumentoService.generarDesPlantilla(PLANTILLA_CONSTANCIA, variables);
        } catch (Exception e) {
            throw new RuntimeException("Error al generar DOCX", e);
        }
    }

    private DatosConstancia cargarDatosConstancia(Integer expedienteId) {
        ExpedienteEntity expediente = expedienteRepository.findById(expedienteId)
                .orElseThrow(() -> new InvalidRequestException("Expediente no encontrado: " + expedienteId));

        String folioExpediente = nvl(expediente.getFolioGobierno());
        DetalleAsesoria detalle = folioExpediente.isBlank()
                ? null
                : detalleRepository.findDetalleByFolio(folioExpediente).orElse(null);

        // ✅ CORREGIDO: Buscar la entidad usando el Repository real que ya existe
        DetalleAsesoriaEntity detalleEntity = detalleAsesoriaJpaRepository
                .findByIdExpediente(expediente.getId().longValue())
                .orElse(null);

        // ✅ CORREGIDO: Pasar la entidad al método de crear queja
        QuejaEntity queja = obtenerOCrearQueja(expediente, detalleEntity);

        LocalDateTime ahora = LocalDateTime.now();
        String folioAsesoria = detalle != null ? nvl(detalle.getFolioGobierno()) : "";
        if (folioAsesoria.isBlank()) {
            folioAsesoria = folioExpediente;
        }

        String estatusExpediente = expediente.getEstatusExpediente() != null
                ? nvl(expediente.getEstatusExpediente().getNombre())
                : "";

        String numeroConstancia = generarNumeroConstancia(expedienteId, ahora.getYear());

        TipoTramiteEntity tipoTramite = expediente.getTipoTramite();

        return new DatosConstancia(
                expedienteId, expediente, queja, detalle, folioExpediente, folioAsesoria,
                detalle != null ? nvl(detalle.getNombreCompleto()) : "",
                detalle != null ? nvl(detalle.getProblematica()) : "",
                detalle != null ? nvl(detalle.getNombreAutoridad()) : "",
                detalle != null ? nvl(detalle.getCalificacionActo()) : "",
                estatusExpediente, resolverAsesorEmisor(detalle), ahora, numeroConstancia,
                numeroConstancia + ".docx", tipoTramite);
    }

    // ✅ CORREGIDO: Recibir el parámetro para satisfacer el NOT NULL de la BD
    private QuejaEntity obtenerOCrearQueja(ExpedienteEntity expediente, DetalleAsesoriaEntity detalleAsesoria) {
        if (expediente.getQuejas() != null && !expediente.getQuejas().isEmpty()) {
            return expediente.getQuejas().get(0);
        }

        QuejaEntity queja = quejaRepository.findByExpediente_Id(expediente.getId()).orElse(null);
        if (queja != null) {
            return queja;
        }

        log.warn("Creando nueva queja para expediente {}", expediente.getId());
        QuejaEntity nuevaQueja = QuejaEntity.builder()
                .expediente(expediente)
                .asesor(expediente.getAsesor())
                .detalleAsesoria(detalleAsesoria) // ✅ SOLUCIÓN AL ERROR 500
                .estatusQueja(1L)
                .fechaRegistro(LocalDateTime.now())
                .ultimaActualizacion(LocalDateTime.now())
                .build();

        return quejaRepository.save(nuevaQueja);
    }

    private Map<String, String> construirVariablesTemplate(DatosConstancia datos, GenerarConstanciaRequest request) {
        Map<String, String> variables = new LinkedHashMap<>();
        variables.put("{{FOLIO_EXPEDIENTE}}", normalizarTextoDocx(datos.folioExpediente()));
        variables.put("{{CONTRIBUYENTE}}", normalizarTextoDocx(resolverNombreContribuyente(datos)));
        variables.put("{{FECHA_EMISION}}", normalizarTextoDocx(datos.fechaGeneracion().format(FMT_FECHA_DOCUMENTO)));

        // ✅ REEMPLAZAR LOS HARDCODEADOS POR LAS VARIABLES DEL REQUEST
        variables.put("{{AREA_QUE_REMITE}}", normalizarTextoDocx(nvl(request.getAreaQueRemite())));
        variables.put("{{AREA_QUE_RECIBE}}", normalizarTextoDocx(nvl(request.getAreaQueRecibe())));
        variables.put("{{SERVICIO_PREVIO}}", normalizarTextoDocx(nvl(request.getServicioPrestado())));

        variables.put("{{DOCUMENTACION_REMITE}}", normalizarTextoDocx(request.getDocumentacionRemite()));
        variables.put("{{MOTIVOS_REMITE}}", normalizarTextoDocx(request.getMotivosRemite()));
        variables.put("{{OBSERVACIONES}}", normalizarTextoDocx(nvl(request.getObservaciones())));

        // ✅ NUEVAS VARIABLES PARA LAS FIRMAS
        variables.put("{{NOMBRE_ASESOR_EMITE}}", normalizarTextoDocx(datos.asesorEmisor()));
        variables.put("{{NOMBRE_ASESOR_RECIBE}}", normalizarTextoDocx(nvl(request.getAsesorQueRecibe())));
        variables.put("{{NOMBRE_TITULAR}}", normalizarTextoDocx(nvl(request.getNombreEncargado())));

        variables.put("{{FUNDAMENTOS}}", normalizarTextoDocx(nvl(request.getFundamentos())));

        return variables;
    }

    private String obtenerMotivoBloqueo(DatosConstancia datos) {
        if (datos.folioExpediente().isBlank()) {
            return "El expediente no tiene folio de gobierno";
        }
        // if (!esCalificacionPositiva(datos.calificacionJuridica())) {
        //     return "Calificación jurídica no es positiva";
        // }
        // if (!esAprobadaParaAdmision(datos.estatusExpediente())) {
        //     return "Estatus no es 'Aprobada para Admisión'";
        // }

        List<String> faltantes = new ArrayList<>();
        if (datos.folioAsesoria().isBlank())
            faltantes.add("Folio de asesoría");
        if (datos.nombreQuejoso().isBlank())
            faltantes.add("Quejoso");
        if (datos.autoridadResponsable().isBlank())
            faltantes.add("Autoridad");
        if (datos.asunto().isBlank())
            faltantes.add("Asunto");
        if (!faltantes.isEmpty()) {
            return "Campos incompletos: " + String.join(", ", faltantes);
        }

        if (existeConstanciaPrevia(datos.queja())) {
            return "Ya existe una Constancia para este expediente";
        }
        return null;
    }

    private void validarRequest(GenerarConstanciaRequest request) {
        if (request == null)
            throw new InvalidRequestException("Body requerido");
        if (nvl(request.getDocumentacionRemite()).isBlank())
            throw new InvalidRequestException("Documentación remite requerida");
        if (nvl(request.getMotivosRemite()).isBlank())
            throw new InvalidRequestException("Motivos remite requeridos");
    }

    private void validarExpedienteId(Integer expedienteId) {
        if (expedienteId == null || expedienteId <= 0)
            throw new InvalidRequestException("ExpedienteId inválido");
    }

    private String resolverAsesorEmisor(DetalleAsesoria detalle) {
        String username = resolverUsernameDesdeSecurityContext();
        if (!username.isBlank())
            return username;
        if (detalle != null) {
            String nombreAsesor = nvl(detalle.getNombreAsesor());
            if (!nombreAsesor.isBlank())
                return nombreAsesor;
        }
        return FALLBACK_ASESOR;
    }

    private String resolverUsernameDesdeSecurityContext() {
        try {
            Class<?> holderClass = Class.forName("org.springframework.security.core.context.SecurityContextHolder");
            Object context = holderClass.getMethod("getContext").invoke(null);
            if (context == null)
                return "";
            Object authentication = context.getClass().getMethod("getAuthentication").invoke(context);
            if (authentication == null)
                return "";
            Object isAuthenticated = authentication.getClass().getMethod("isAuthenticated").invoke(authentication);
            if (!(isAuthenticated instanceof Boolean authenticated) || !authenticated)
                return "";
            Object name = authentication.getClass().getMethod("getName").invoke(authentication);
            String username = nvl(name != null ? name.toString() : "");
            if ("anonymousUser".equalsIgnoreCase(username))
                return "";
            return username;
        } catch (Exception ex) {
            return "";
        }
    }

    private String generarNumeroConstancia(Integer expedienteId, int anio) {
        return "CONST-REMISION-" + anio + "-" + String.format("%05d", expedienteId);
    }

    private boolean existeConstanciaPrevia(QuejaEntity queja) {
        if (queja == null || queja.getIdQueja() == null)
            return false;
        return cirRepository.existeByQuejaId(queja.getIdQueja().longValue());
    }

    private String extractNombreArchivoFromUrl(String url) {
        if (url == null || url.isEmpty())
            return "";
        return url.substring(url.lastIndexOf('/') + 1);
    }

    // private boolean esCalificacionPositiva(String value) {
    //     String t = normalizarSoloLetras(value);
    //     return t.equals("procede") || t.equals("prevencion subsanada");
    // }

    // private boolean esAprobadaParaAdmision(String value) {
    //     String t = normalizar(value);
    //     return t.contains("aprobada") && t.contains("admision");
    // }

    private String nvl(String value) {
        return value != null ? value.trim() : "";
    }

    private String normalizar(String value) {
        if (value == null)
            return "";
        return Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "").trim().toLowerCase();
    }

    private String normalizarSoloLetras(String value) {
        String t = normalizar(value);
        t = t.replaceAll("[^a-z\\s]", " ");
        return t.replaceAll("\\s+", " ").trim();
    }

    private String normalizarTextoDocx(String value) {
        return nvl(value).replace("\r\n", "\n").replace("\r", "\n");
    }

    private String resolverNombreContribuyente(DatosConstancia datos) {
        if (!nvl(datos.nombreQuejoso()).isBlank())
            return datos.nombreQuejoso();
        if (datos.expediente().getContribuyente() == null || datos.expediente().getContribuyente().getPersona() == null)
            return "";
        return nombreCompletoPersona(datos.expediente().getContribuyente().getPersona());
    }

    private String nombreCompletoPersona(PersonaEntity persona) {
        StringBuilder sb = new StringBuilder();
        appendSegment(sb, persona.getNombre());
        appendSegment(sb, persona.getApellidoPaterno());
        appendSegment(sb, persona.getApellidoMaterno());
        return sb.toString();
    }

    private void appendSegment(StringBuilder sb, String value) {
        String texto = nvl(value);
        if (texto.isBlank())
            return;
        if (sb.length() > 0)
            sb.append(' ');
        sb.append(texto);
    }

    private record DatosConstancia(
            Integer expedienteId,
            ExpedienteEntity expediente,
            QuejaEntity queja,
            DetalleAsesoria detalle,
            String folioExpediente,
            String folioAsesoria,
            String nombreQuejoso,
            String asunto,
            String autoridadResponsable,
            String calificacionJuridica,
            String estatusExpediente,
            String asesorEmisor,
            LocalDateTime fechaGeneracion,
            String numeroConstancia,
            String nombreArchivo,
            TipoTramiteEntity tipoTramite) {
    }

    public record ConstanciaGenerada(String nombreArchivo, String url, byte[] archivo) {
    }
}