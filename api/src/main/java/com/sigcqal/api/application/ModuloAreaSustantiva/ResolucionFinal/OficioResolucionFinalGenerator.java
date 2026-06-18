package com.sigcqal.api.application.ModuloAreaSustantiva.ResolucionFinal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * Rellena plantilla_oficio.docx (ubicada en resources/templates/) sustituyendo
 * los marcadores {{PLACEHOLDER}} por los valores reales, sin perder el
 * formato (negritas, colores, etc.) de la plantilla original.
 *
 * IMPORTANTE: la plantilla debe colocarse en
 * src/main/resources/templates/plantilla_oficio.docx
 */
@Component
public class OficioResolucionFinalGenerator {

    private static final String TEMPLATE_PATH = "templates/plantilla_oficio.docx";

    public byte[] generar(Map<String, String> placeholders) throws IOException {
        try (InputStream is = new ClassPathResource(TEMPLATE_PATH).getInputStream();
             XWPFDocument document = new XWPFDocument(is);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            reemplazarEnParrafos(document.getParagraphs(), placeholders);
            reemplazarEnTablas(document.getTables(), placeholders);
            reemplazarEnHeadersFooters(document, placeholders);

            document.write(out);
            return out.toByteArray();
        }
    }

    private void reemplazarEnHeadersFooters(XWPFDocument document, Map<String, String> placeholders) {
        document.getHeaderList().forEach(header ->
                reemplazarEnParrafos(header.getParagraphs(), placeholders));
        document.getFooterList().forEach(footer ->
                reemplazarEnParrafos(footer.getParagraphs(), placeholders));
    }

    private void reemplazarEnTablas(List<XWPFTable> tablas, Map<String, String> placeholders) {
        for (XWPFTable tabla : tablas) {
            tabla.getRows().forEach(fila ->
                    fila.getTableCells().forEach(celda ->
                            reemplazarEnParrafos(celda.getParagraphs(), placeholders)));
        }
    }

    /**
     * Reemplaza el placeholder respetando que Word puede partir
     * "{{FOLIO}}" en varios <w:r> distintos. Se reconstruye el texto
     * completo del párrafo, se sustituye, y se reescribe en un solo run
     * tomando el formato del primer run como base.
     */
    private void reemplazarEnParrafos(List<XWPFParagraph> paragraphs, Map<String, String> placeholders) {
        for (XWPFParagraph paragraph : paragraphs) {
            String textoCompleto = paragraph.getText();
            if (textoCompleto == null || !textoCompleto.contains("{{")) {
                continue;
            }

            String textoReemplazado = textoCompleto;
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                textoReemplazado = textoReemplazado.replace(
                        "{{" + entry.getKey() + "}}",
                        entry.getValue() != null ? entry.getValue() : "");
            }

            if (!textoReemplazado.equals(textoCompleto)) {
                List<XWPFRun> runs = paragraph.getRuns();
                if (!runs.isEmpty()) {
                    // Conserva el formato del primer run, limpia el resto
                    runs.get(0).setText(textoReemplazado, 0);
                    for (int i = runs.size() - 1; i >= 1; i--) {
                        paragraph.removeRun(i);
                    }
                }
            }
        }
    }
}