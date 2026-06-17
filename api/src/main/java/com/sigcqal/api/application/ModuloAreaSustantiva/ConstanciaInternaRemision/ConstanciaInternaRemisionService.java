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
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.sigcqal.api.application.ModuloCorrespondencia.Documento.GeneradorDocumentoService;
import com.sigcqal.api.application.exception.DuplicateResourceException;
import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.DetalleAsesoria.Model.DetalleAsesoria;
import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Entity.EstatusExpedienteEntity;
import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Repository.EstatusExpedienteJpaRepository;
import com.sigcqal.api.infra.Catalogo.Persona.Entity.PersonaEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.DetalleAsesoria.Repository.DetalleAsesoriaRepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Repository.ExpedienteJPARepository;
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
    private static final DateTimeFormatter FMT_FECHA_DOCUMENTO = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", LOCALE_ES_MX);
    private static final String ESTATUS_CONSTANCIA_EMITIDA = "Constancia Emitida";
    private static final String FALLBACK_ASESOR = "ASESOR NO DISPONIBLE";
    private static final String PLANTILLA_CONSTANCIA = "plantilla_constancia_interna_remision.docx";
    private static final String AREA_CANALIZAR = "Representación Legal y Defensa";
    private static final String SERVICIO_PREVIO = "Quejas";

    private final DetalleAsesoriaRepository detalleRepository;
    private final ExpedienteJPARepository expedienteRepository;
    private final EstatusExpedienteJpaRepository estatusRepository;
    private final FileUploadPort fileUploadPort;
    private final GeneradorDocumentoService generadorDocumentoService;

    public ConstanciaPreviewResponse obtenerPreview(String folio) {
        var expedienteOpt = expedienteRepository.findByFolioGobierno(folio);
        if (expedienteOpt.isEmpty()) {
            throw new ResourceNotFoundException("Expediente", null);
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
            .fechaGeneracion(datos.fechaGeneracion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", LOCALE_ES_MX)))
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

        EstatusExpedienteEntity estatusConstancia = estatusRepository.findByNombreIgnoreCase(ESTATUS_CONSTANCIA_EMITIDA)
            .orElseThrow(() -> new InvalidRequestException("No se encontró el estatus de expediente requerido: " + ESTATUS_CONSTANCIA_EMITIDA));

        datos.expediente().setEstatusExpediente(estatusConstancia);
        expedienteRepository.save(datos.expediente());

        log.info(
            "Constancia interna de remisión generada expedienteId={} usuario={} ip={} archivo={} fecha={}",
            datos.expedienteId(),
            datos.asesorEmisor(),
            nvl(request.getIpCliente()),
            datos.nombreArchivo(),
            LocalDateTime.now()
        );

        return new ConstanciaGenerada(datos.nombreArchivo(), url, archivo);
    }

    public Optional<ConstanciaGenerada> obtenerExistente(String folio) {
        var expedienteOpt = expedienteRepository.findByFolioGobierno(folio);
        if (expedienteOpt.isEmpty() || expedienteOpt.get().getId() == null) {
            return Optional.empty();
        }

        Optional<Path> archivoOpt = encontrarArchivoConstancia(expedienteOpt.get().getId());
        if (archivoOpt.isEmpty()) {
            return Optional.empty();
        }

        try {
            String nombreArchivo = archivoOpt.get().getFileName().toString();
            byte[] contenido = Files.readAllBytes(archivoOpt.get());
            String url = "/api/files/constancias/" + nombreArchivo;
            return Optional.of(new ConstanciaGenerada(nombreArchivo, url, contenido));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public String generarHtmlPreview(GenerarConstanciaRequest request) {
        try {
            String htmlTemplate = loadTemplate("cir_preview.html");
            String membreteBase64 = loadMembreteBase64();

            String html = htmlTemplate
                .replace("{{MEMBRETE_BASE64}}", membreteBase64)
                .replace("{{FUNDAMENTOS}}", sanitize(request.getDocumentacionRemite()))
                .replace("{{FUNDAMENTOS_CLASS}}", request.getDocumentacionRemite() != null && !request.getDocumentacionRemite().isBlank() ? "" : "content-empty")
                .replace("{{OBSERVACIONES}}", sanitize(request.getObservaciones()))
                .replace("{{OBSERVACIONES_CLASS}}", request.getObservaciones() != null && !request.getObservaciones().isBlank() ? "" : "content-empty")
                .replace("{{FECHA_CIR}}", request.getFechaCIR() != null ? request.getFechaCIR() : "")
                .replace("{{FECHA_CLASS}}", request.getFechaCIR() != null && !request.getFechaCIR().isBlank() ? "" : "content-empty")
                .replace("{{ASESOR_QUEMITE}}", sanitize(request.getAsesorQueRemite()))
                .replace("{{NOMBRE_ENCARGADO}}", sanitize(request.getNombreEncargado()))
                .replace("{{FECHA_GENERACION}}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

            return html;
        } catch (Exception e) {
            throw new RuntimeException("Error al generar HTML preview: " + e.getMessage(), e);
        }
    }

    private String loadMembreteBase64() {
        try {
            ClassPathResource resource = new ClassPathResource("assets/membrete.jpg");
            if (!resource.exists()) {
                return "";
            }
            byte[] bytes = resource.getInputStream().readAllBytes();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            return "";
        }
    }

    private String loadTemplate(String templateName) throws IOException {
        ClassPathResource resource = new ClassPathResource("plantillas/" + templateName);
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

    private String sanitize(String input) {
        if (input == null) return "";
        return input.replaceAll("&", "&amp;")
            .replaceAll("<", "&lt;")
            .replaceAll(">", "&gt;")
            .replaceAll("\r\n|\r|\n", "<br/>");
    }

    private byte[] generarDocx(Map<String, String> variables) {
        try {
            return generadorDocumentoService.generarDesPlantilla(PLANTILLA_CONSTANCIA, variables);
        } catch (Exception e) {
            throw new RuntimeException("Error al generar la constancia en formato DOCX.", e);
        }
    }

    private DatosConstancia cargarDatosConstancia(Integer expedienteId) {
        ExpedienteEntity expediente = expedienteRepository.findById(expedienteId)
            .orElseThrow(() -> new ResourceNotFoundException("Expediente", expedienteId.longValue()));

        String folioExpediente = nvl(expediente.getFolioGobierno());
        DetalleAsesoria detalle = folioExpediente.isBlank()
            ? null
            : detalleRepository.findDetalleByFolio(folioExpediente).orElse(null);

        LocalDateTime ahora = LocalDateTime.now();
        String folioAsesoria = detalle != null ? nvl(detalle.getFolioGobierno()) : "";
        if (folioAsesoria.isBlank()) {
            folioAsesoria = folioExpediente;
        }

        String estatusExpediente = expediente.getEstatusExpediente() != null
            ? nvl(expediente.getEstatusExpediente().getNombre())
            : "";

        String numeroConstancia = generarNumeroConstancia(expedienteId, ahora.getYear());

        return new DatosConstancia(
            expedienteId,
            expediente,
            detalle,
            folioExpediente,
            folioAsesoria,
            detalle != null ? nvl(detalle.getNombreCompleto()) : "",
            detalle != null ? nvl(detalle.getProblematica()) : "",
            detalle != null ? nvl(detalle.getNombreAutoridad()) : "",
            detalle != null ? nvl(detalle.getCalificacionActo()) : "",
            estatusExpediente,
            resolverAsesorEmisor(detalle),
            ahora,
            numeroConstancia,
            numeroConstancia + ".docx"
        );
    }

    private Map<String, String> construirVariablesTemplate(DatosConstancia datos, GenerarConstanciaRequest request) {
        Map<String, String> variables = new LinkedHashMap<>();
        variables.put("{{FOLIO_EXPEDIENTE}}", normalizarTextoDocx(datos.folioExpediente()));
        variables.put("{{CONTRIBUYENTE}}", normalizarTextoDocx(resolverNombreContribuyente(datos)));
        variables.put("{{FECHA_EMISION}}", normalizarTextoDocx(datos.fechaGeneracion().format(FMT_FECHA_DOCUMENTO)));
        variables.put("{{AREA_CANALIZAR}}", AREA_CANALIZAR);
        variables.put("{{SERVICIO_PREVIO}}", SERVICIO_PREVIO);
        variables.put("{{DOCUMENTACION_REMITE}}", normalizarTextoDocx(request.getDocumentacionRemite()));
        variables.put("{{MOTIVOS_REMITE}}", normalizarTextoDocx(request.getMotivosRemite()));
        variables.put("{{OBSERVACIONES}}", normalizarTextoDocx(nvl(request.getObservaciones())));
        variables.put("{{NOMBRE_ASESOR_EMITE}}", normalizarTextoDocx(datos.asesorEmisor()));
        return variables;
    }

    private String obtenerMotivoBloqueo(DatosConstancia datos) {
        if (datos.folioExpediente().isBlank()) {
            return "El expediente no tiene folio de gobierno";
        }
        if (!esCalificacionPositiva(datos.calificacionJuridica())) {
            return "No es posible generar la Constancia. El expediente requiere calificación jurídica positiva conforme a SCRUM-9.";
        }
        if (!esAprobadaParaAdmision(datos.estatusExpediente())) {
            return "No es posible generar la Constancia. El expediente requiere estatus \"Aprobada para Admisión\".";
        }

        List<String> faltantes = new ArrayList<>();
        if (datos.folioAsesoria().isBlank()) faltantes.add("Folio de asesoría");
        if (datos.nombreQuejoso().isBlank()) faltantes.add("Quejoso / Contribuyente");
        if (datos.autoridadResponsable().isBlank()) faltantes.add("Autoridad responsable");
        if (datos.asunto().isBlank()) faltantes.add("Asunto");
        if (!faltantes.isEmpty()) {
            return "No se puede generar la Constancia. Verifique que los siguientes campos estén completos: "
                + String.join(", ", faltantes) + ".";
        }

        if (existeConstanciaPrevia(datos.expedienteId())) {
            return "Ya existe una Constancia generada para este expediente.";
        }
        return null;
    }

    private void validarRequest(GenerarConstanciaRequest request) {
        if (request == null) {
            throw new InvalidRequestException("El body es requerido");
        }
        if (nvl(request.getDocumentacionRemite()).isBlank()) {
            throw new InvalidRequestException("La documentación que se remite es requerida");
        }
        if (nvl(request.getMotivosRemite()).isBlank()) {
            throw new InvalidRequestException("Los motivos por los que se remite son requeridos");
        }
    }

    private void validarExpedienteId(Integer expedienteId) {
        if (expedienteId == null || expedienteId <= 0) {
            throw new InvalidRequestException("El expedienteId debe ser mayor a 0");
        }
    }

    private String resolverAsesorEmisor(DetalleAsesoria detalle) {
        String username = resolverUsernameDesdeSecurityContext();
        if (!username.isBlank()) {
            return username;
        }

        if (detalle != null) {
            String nombreAsesor = nvl(detalle.getNombreAsesor());
            if (!nombreAsesor.isBlank()) {
                return nombreAsesor;
            }
        }

        return FALLBACK_ASESOR;
    }

    private String resolverUsernameDesdeSecurityContext() {
        try {
            Class<?> holderClass = Class.forName("org.springframework.security.core.context.SecurityContextHolder");
            Object context = holderClass.getMethod("getContext").invoke(null);
            if (context == null) {
                return "";
            }

            Object authentication = context.getClass().getMethod("getAuthentication").invoke(context);
            if (authentication == null) {
                return "";
            }

            Object isAuthenticated = authentication.getClass().getMethod("isAuthenticated").invoke(authentication);
            if (!(isAuthenticated instanceof Boolean authenticated) || !authenticated) {
                return "";
            }

            Object name = authentication.getClass().getMethod("getName").invoke(authentication);
            String username = nvl(name != null ? name.toString() : "");
            if ("anonymousUser".equalsIgnoreCase(username)) {
                return "";
            }
            return username;
        } catch (Exception ex) {
            return "";
        }
    }

    private String generarNumeroConstancia(Integer expedienteId, int anio) {
        return "CONST-REMISION-" + anio + "-" + String.format("%05d", expedienteId);
    }

    private boolean existeConstanciaPrevia(Integer expedienteId) {
        return encontrarArchivoConstancia(expedienteId).isPresent();
    }

    private Optional<Path> encontrarArchivoConstancia(Integer expedienteId) {
        String consecutivo = String.format("%05d", expedienteId);
        Path root = Paths.get(".").toAbsolutePath().normalize();
        Path dir = root.resolve("uploads/constancias/");
        if (!Files.exists(dir)) {
            return Optional.empty();
        }

        Pattern pattern = Pattern.compile("^CONST-REMISION-(\\d{4})-" + Pattern.quote(consecutivo) + "\\.(pdf|docx)$");

        try (Stream<Path> stream = Files.list(dir)) {
            return stream
                .filter((p) -> p.getFileName() != null)
                .filter((p) -> pattern.matcher(p.getFileName().toString()).matches())
                .max(Comparator.comparingInt((p) -> extraerAnioArchivo(pattern, p)));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private int extraerAnioArchivo(Pattern pattern, Path path) {
        Matcher matcher = pattern.matcher(path.getFileName().toString());
        if (!matcher.matches()) {
            return 0;
        }
        return Integer.parseInt(matcher.group(1));
    }

    private boolean esCalificacionPositiva(String value) {
        String t = normalizarSoloLetras(value);
        return t.equals("procede") || t.equals("prevencion subsanada");
    }

    private boolean esAprobadaParaAdmision(String value) {
        String t = normalizar(value);
        return t.contains("aprobada") && t.contains("admision");
    }

    private String nvl(String value) {
        return value != null ? value.trim() : "";
    }

    private String normalizar(String value) {
        if (value == null) {
            return "";
        }
        return Normalizer.normalize(value, Normalizer.Form.NFD)
            .replaceAll("\\p{M}", "")
            .trim()
            .toLowerCase();
    }

    private String normalizarSoloLetras(String value) {
        String t = normalizar(value);
        t = t.replaceAll("[^a-z\\s]", " ");
        t = t.replaceAll("\\s+", " ").trim();
        return t;
    }

    private String normalizarTextoDocx(String value) {
        return nvl(value)
            .replace("\r\n", "\n")
            .replace("\r", "\n");
    }

    private String resolverNombreContribuyente(DatosConstancia datos) {
        if (!nvl(datos.nombreQuejoso()).isBlank()) {
            return datos.nombreQuejoso();
        }
        if (datos.expediente().getContribuyente() == null || datos.expediente().getContribuyente().getPersona() == null) {
            return "";
        }
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
        if (texto.isBlank()) {
            return;
        }
        if (sb.length() > 0) {
            sb.append(' ');
        }
        sb.append(texto);
    }

    private record DatosConstancia(
        Integer expedienteId,
        ExpedienteEntity expediente,
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
        String nombreArchivo
    ) {}

    public record ConstanciaGenerada(String nombreArchivo, String url, byte[] archivo) {}
}
