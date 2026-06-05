package com.sigcqal.api.application.ModuloAreaSustantiva.ConstanciaInternaRemision;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashMap;
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
import com.sigcqal.api.application.exception.InvalidRequestException;
import com.sigcqal.api.application.exception.DuplicateResourceException;
import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.domain.ModuloAreaSustantiva.DetalleAsesoria.Model.DetalleAsesoria;
import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Entity.EstatusExpedienteEntity;
import com.sigcqal.api.infra.Catalogo.EstatusExpediente.Repository.EstatusExpedienteJpaRepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.DetalleAsesoria.Repository.DetalleAsesoriaRepository;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Repository.ExpedienteJPARepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConstanciaInternaRemisionService {

    private static final DateTimeFormatter FMT_TS =
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", new Locale("es", "MX"));

    private final DetalleAsesoriaRepository detalleRepository;
    private final ExpedienteJPARepository expedienteRepository;
    private final EstatusExpedienteJpaRepository estatusRepository;
    private final FileUploadPort fileUploadPort;

    @Transactional
    public ConstanciaGenerada generar(String folio) {
        DetalleAsesoria detalle = detalleRepository.findDetalleByFolio(folio)
            .orElseThrow(() -> new InvalidRequestException("Expediente no encontrado con folio: " + folio));

        String calificacion = nvl(detalle.getCalificacionActo());
        if (!esCalificacionPositiva(calificacion)) {
            throw new InvalidRequestException(
                "No es posible generar la Constancia. El expediente requiere calificación jurídica positiva conforme a SCRUM-9."
            );
        }

        ExpedienteEntity expediente = expedienteRepository.findFirstByFolioGobierno(folio)
            .orElseThrow(() -> new InvalidRequestException("Expediente no encontrado con folio: " + folio));

        String estatusActual = expediente.getEstatusExpediente() != null ? expediente.getEstatusExpediente().getNombre() : "";
        if (!esAprobadaParaAdmision(estatusActual)) {
            throw new InvalidRequestException("No es posible generar la Constancia. El expediente requiere estatus \"Aprobada para Admisión\".");
        }

        Integer idExpediente = expediente.getId();
        String consecutivo = String.format("%04d", idExpediente);
        int anio = LocalDate.now().getYear();
        String nombreArchivo = "CONST-REMISION-" + anio + "-" + consecutivo + ".pdf";

        Path root = Paths.get(".").toAbsolutePath().normalize();
        Path archivo = root.resolve("uploads/constancias/" + nombreArchivo);
        if (Files.exists(archivo)) {
            throw new DuplicateResourceException("Ya existe una Constancia generada para este expediente en esta fecha.");
        }

        Map<String, String> variables = new LinkedHashMap<>();
        variables.put("{{NUM_CONSTANCIA}}", "CONST-REMISION-" + anio + "-" + consecutivo);
        variables.put("{{FECHA_HORA}}", LocalDateTime.now().format(FMT_TS));
        variables.put("{{FOLIO_ASESORIA}}", nvl(detalle.getFolioGobierno()));
        variables.put("{{CALIFICACION_JURIDICA}}", calificacion);
        variables.put("{{QUEJOSO}}", nvl(detalle.getNombreCompleto()));
        variables.put("{{AUTORIDAD_RESPONSABLE}}", nvl(detalle.getNombreAutoridad()));
        variables.put("{{DESCRIPCION_SINTETICA}}", nvl(detalle.getProblematica()));

        java.util.List<String> faltantes = new java.util.ArrayList<>();
        if (variables.get("{{QUEJOSO}}").isBlank()) faltantes.add("Quejoso / Contribuyente");
        if (variables.get("{{AUTORIDAD_RESPONSABLE}}").isBlank()) faltantes.add("Autoridad responsable");
        if (variables.get("{{DESCRIPCION_SINTETICA}}").isBlank()) faltantes.add("Descripción sintética");
        if (!faltantes.isEmpty()) {
            throw new InvalidRequestException(
                "No se puede generar la Constancia. Verifique que los siguientes campos estén completos: " +
                String.join(", ", faltantes) + "."
            );
        }

        String html = cargarPlantillaHtml("plantillas/constancia_interna_remision.html", variables);
        byte[] pdf = renderizarPdf(html);
        byte[] pdfProtegido = protegerPdf(pdf);

        String url = fileUploadPort.guardarArchivoConstancia(pdfProtegido, nombreArchivo);

        EstatusExpedienteEntity estatusConstancia = estatusRepository.findByNombreIgnoreCase("Constancia Emitida")
            .orElseThrow(() -> new InvalidRequestException("No se encontró el estatus de expediente requerido: Constancia Emitida"));

        expediente.setEstatusExpediente(estatusConstancia);
        expedienteRepository.save(expediente);

        return new ConstanciaGenerada(nombreArchivo, url, pdfProtegido);
    }

    public Optional<ConstanciaGenerada> obtenerExistente(String folio) {
        var expedienteOpt = expedienteRepository.findFirstByFolioGobierno(folio);
        if (expedienteOpt.isEmpty()) return Optional.empty();

        Integer idExpediente = expedienteOpt.get().getId();
        if (idExpediente == null) return Optional.empty();

        String consecutivo = String.format("%04d", idExpediente);
        Path root = Paths.get(".").toAbsolutePath().normalize();
        Path dir = root.resolve("uploads/constancias/");
        if (!Files.exists(dir)) return Optional.empty();

        Pattern pattern = Pattern.compile("^CONST-REMISION-(\\d{4})-" + Pattern.quote(consecutivo) + "\\.pdf$");

        try (Stream<Path> stream = Files.list(dir)) {
            Optional<Path> archivoOpt = stream
                .filter((p) -> p.getFileName() != null)
                .filter((p) -> pattern.matcher(p.getFileName().toString()).matches())
                .max(Comparator.comparingInt((p) -> {
                    Matcher m = pattern.matcher(p.getFileName().toString());
                    if (!m.matches()) return 0;
                    return Integer.parseInt(m.group(1));
                }));

            if (archivoOpt.isEmpty()) return Optional.empty();
            String nombreArchivo = archivoOpt.get().getFileName().toString();
            byte[] pdf = Files.readAllBytes(archivoOpt.get());
            String url = "/api/files/constancias/" + nombreArchivo;
            return Optional.of(new ConstanciaGenerada(nombreArchivo, url, pdf));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private String cargarPlantillaHtml(String classpath, Map<String, String> variables) {
        try {
            ClassPathResource resource = new ClassPathResource(classpath);
            try (InputStream is = resource.getInputStream()) {
                String html = new String(is.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
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

    private String nvl(String value) {
        return value != null ? value.trim() : "";
    }

    private String normalizar(String value) {
        if (value == null) return "";
        return java.text.Normalizer.normalize(value, java.text.Normalizer.Form.NFD)
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

    public record ConstanciaGenerada(String nombreArchivo, String url, byte[] pdf) {}
}
