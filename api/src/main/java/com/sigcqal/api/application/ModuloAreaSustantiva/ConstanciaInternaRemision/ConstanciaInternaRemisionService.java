package com.sigcqal.api.application.ModuloAreaSustantiva.ConstanciaInternaRemision;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.sigcqal.api.application.exception.DuplicateResourceException;
import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.ResourceNotFoundException;
import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.DetalleAsesoria.Model.DetalleAsesoria;
import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Entity.EstatusExpedienteEntity;
import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Repository.EstatusExpedienteJpaRepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.DetalleAsesoria.Repository.DetalleAsesoriaRepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Repository.ExpedienteJPARepository;
import com.sigcqal.api.web.ModuloAreaSustantiva.ConstanciaInternaRemision.Dto.ConstanciaPreviewResponse;
import com.sigcqal.api.web.ModuloAreaSustantiva.ConstanciaInternaRemision.Dto.GenerarConstanciaRequest;
import com.sigcqal.api.web.ModuloAreaSustantiva.ConstanciaInternaRemision.Dto.GenerarConstanciaResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConstanciaInternaRemisionService {

    private static final Locale LOCALE_ES_MX = new Locale("es", "MX");
    private static final DateTimeFormatter FMT_TS = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", LOCALE_ES_MX);
    private static final DateTimeFormatter FMT_HORA = DateTimeFormatter.ofPattern("HH:mm", LOCALE_ES_MX);
    private static final DateTimeFormatter FMT_DIA = DateTimeFormatter.ofPattern("dd", LOCALE_ES_MX);
    private static final DateTimeFormatter FMT_ANIO = DateTimeFormatter.ofPattern("yyyy", LOCALE_ES_MX);
    private static final String ESTATUS_CONSTANCIA_EMITIDA = "Constancia Emitida";
    private static final String FALLBACK_ASESOR = "ASESOR NO DISPONIBLE";
    private static final String AREA_RECEPCION = "Sello Digital de Recepción";

    private final DetalleAsesoriaRepository detalleRepository;
    private final ExpedienteJPARepository expedienteRepository;
    private final EstatusExpedienteJpaRepository estatusRepository;
    private final FileUploadPort fileUploadPort;

    public ConstanciaPreviewResponse obtenerPreview(Integer expedienteId) {
        validarExpedienteId(expedienteId);
        DatosConstancia datos = cargarDatosConstancia(expedienteId);
        String motivoBloqueo = obtenerMotivoBloqueo(datos);

        return ConstanciaPreviewResponse.builder()
            .expedienteId(datos.expedienteId())
            .folioAsesoria(datos.folioAsesoria())
            .nombreQuejoso(datos.nombreQuejoso())
            .asunto(datos.asunto())
            .autoridadResponsable(datos.autoridadResponsable())
            .fechaGeneracion(datos.fechaGeneracion().format(FMT_TS))
            .numeroConstancia(datos.numeroConstancia())
            .asesorEmisor(datos.asesorEmisor())
            .puedeGenerar(motivoBloqueo == null)
            .motivoBloqueo(motivoBloqueo)
            .build();
    }

    @Transactional
    public GenerarConstanciaResponse generar(Integer expedienteId, GenerarConstanciaRequest request) {
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

        String html = cargarPlantillaHtml(
            "plantillas/constancia_interna_remision.html",
            construirVariablesTemplate(datos, request)
        );
        byte[] pdf = renderizarPdf(html);
        byte[] pdfProtegido = protegerPdf(pdf);
        String url = fileUploadPort.guardarArchivoConstancia(pdfProtegido, datos.nombreArchivo());

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

        return GenerarConstanciaResponse.builder()
            .idConstancia(datos.numeroConstancia())
            .nombreArchivo(datos.nombreArchivo())
            .urlDescarga(url)
            .fechaGeneracion(LocalDateTime.now())
            .estatusExpediente(ESTATUS_CONSTANCIA_EMITIDA)
            .mensaje("Constancia Interna de Remisión generada exitosamente.")
            .puedeDescargar(url != null && !url.isBlank())
            .build();
    }

    public Optional<ConstanciaGenerada> obtenerExistente(String folio) {
        var expedienteOpt = expedienteRepository.findFirstByFolioGobierno(folio);
        if (expedienteOpt.isEmpty() || expedienteOpt.get().getId() == null) {
            return Optional.empty();
        }

        Optional<Path> archivoOpt = encontrarArchivoConstancia(expedienteOpt.get().getId());
        if (archivoOpt.isEmpty()) {
            return Optional.empty();
        }

        try {
            String nombreArchivo = archivoOpt.get().getFileName().toString();
            byte[] pdf = Files.readAllBytes(archivoOpt.get());
            String url = "/api/files/constancias/" + nombreArchivo;
            return Optional.of(new ConstanciaGenerada(nombreArchivo, url, pdf));
        } catch (Exception e) {
            return Optional.empty();
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
            numeroConstancia + ".pdf"
        );
    }

    private Map<String, String> construirVariablesTemplate(DatosConstancia datos, GenerarConstanciaRequest request) {
        Map<String, String> variables = new LinkedHashMap<>();
        variables.put("{{NUM_CONSTANCIA}}", escapeHtml(datos.numeroConstancia()));
        variables.put("{{FECHA_GENERACION}}", escapeHtml(datos.fechaGeneracion().format(FMT_TS)));
        variables.put("{{HORA}}", escapeHtml(datos.fechaGeneracion().format(FMT_HORA)));
        variables.put("{{DIA}}", escapeHtml(datos.fechaGeneracion().format(FMT_DIA)));
        variables.put("{{MES}}", escapeHtml(
            datos.fechaGeneracion().getMonth().getDisplayName(TextStyle.FULL, LOCALE_ES_MX).toUpperCase(LOCALE_ES_MX)
        ));
        variables.put("{{ANIO}}", escapeHtml(datos.fechaGeneracion().format(FMT_ANIO)));
        variables.put("{{FOLIO_ASESORIA}}", escapeHtml(datos.folioAsesoria()));
        variables.put("{{QUEJOSO}}", escapeHtml(datos.nombreQuejoso()));
        variables.put("{{ASUNTO}}", escapeHtml(datos.asunto()));
        variables.put("{{AUTORIDAD_RESPONSABLE}}", escapeHtml(datos.autoridadResponsable()));
        variables.put("{{ANALISIS_JURIDICO}}", escapeHtmlMultiline(request.getAnalisisJuridico()));
        variables.put("{{DETERMINACION}}", escapeHtmlMultiline(request.getDeterminacion()));
        variables.put("{{ASESOR_EMISOR}}", escapeHtml(datos.asesorEmisor()));
        variables.put("{{AREA_RECEPCION}}", escapeHtml(AREA_RECEPCION));
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
        if (nvl(request.getAnalisisJuridico()).isBlank()) {
            throw new InvalidRequestException("El analisisJuridico es requerido");
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

        Pattern pattern = Pattern.compile("^CONST-REMISION-(\\d{4})-" + Pattern.quote(consecutivo) + "\\.pdf$");

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

    private String cargarPlantillaHtml(String classpath, Map<String, String> variables) {
        try {
            ClassPathResource resource = new ClassPathResource(classpath);
            try (InputStream is = resource.getInputStream()) {
                String html = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                for (Map.Entry<String, String> e : variables.entrySet()) {
                    html = html.replace(e.getKey(), e.getValue() != null ? e.getValue() : "");
                }
                return html;
            }
        } catch (Exception e) {
            throw new RuntimeException("No se encuentra la plantilla de Constancia Interna de Remisión.", e);
        }
    }

    private byte[] renderizarPdf(String html) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, null);
            builder.toStream(out);
            builder.run();
            return out.toByteArray();
        } catch (Exception e) {
            Throwable root = e;
            while (root.getCause() != null && root.getCause() != root) {
                root = root.getCause();
            }

            String htmlPreview = html == null ? "" : html.substring(0, Math.min(html.length(), 2000));

            log.error(
                "Error renderizando PDF. exceptionType={} message={} rootType={} rootMessage={} htmlPreview={}",
                e.getClass().getName(),
                e.getMessage(),
                root.getClass().getName(),
                root.getMessage(),
                htmlPreview,
                e
            );

            throw new RuntimeException("Error al generar el documento.", e);
        }
    }

    private byte[] protegerPdf(byte[] pdf) {
        try (PDDocument doc = PDDocument.load(pdf);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            AccessPermission ap = new AccessPermission();
            ap.setCanModify(false);
            ap.setCanModifyAnnotations(false);
            ap.setCanFillInForm(false);
            ap.setCanAssembleDocument(false);
            ap.setCanExtractContent(false);
            ap.setCanExtractForAccessibility(false);
            ap.setCanPrint(true);
            ap.setCanPrintDegraded(true);

            String ownerPassword = UUID.randomUUID().toString();
            StandardProtectionPolicy policy = new StandardProtectionPolicy(ownerPassword, "", ap);
            policy.setEncryptionKeyLength(128);
            doc.protect(policy);
            doc.save(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al proteger el PDF.", e);
        }
    }

    private boolean esCalificacionPositiva(String value) {
        String t = normalizarSoloLetras(value);
        return t.equals("procede") || t.equals("prevencion subsanada");
    }

    private boolean esAprobadaParaAdmision(String value) {
        String t = normalizar(value);
        return t.contains("aprobada") && t.contains("admision");
    }

    private String escapeHtml(String value) {
        return nvl(value)
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#39;");
    }

    private String escapeHtmlMultiline(String value) {
        return escapeHtml(value)
            .replace("\r\n", "\n")
            .replace("\r", "\n")
            .replace("\n", "<br/>");
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

    public record ConstanciaGenerada(String nombreArchivo, String url, byte[] pdf) {}
}
