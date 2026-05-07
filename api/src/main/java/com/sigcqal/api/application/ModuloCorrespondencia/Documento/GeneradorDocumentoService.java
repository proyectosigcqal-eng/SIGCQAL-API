package com.sigcqal.api.application.ModuloCorrespondencia.Documento;

import org.apache.poi.xwpf.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class GeneradorDocumentoService {

    private static final DateTimeFormatter FECHA_FORMATO =
        DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "MX"));

    public byte[] generarDesPlantilla(String nombrePlantilla, Map<String, String> variables)
            throws Exception {

        ClassPathResource resource = new ClassPathResource("plantillas/" + nombrePlantilla);

        try (InputStream is = resource.getInputStream();
             XWPFDocument doc = new XWPFDocument(is);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            // Párrafos del cuerpo
            doc.getParagraphs().forEach(p -> reemplazar(p, variables));

            // Tablas
            doc.getTables().forEach(t ->
                t.getRows().forEach(r ->
                    r.getTableCells().forEach(c ->
                        c.getParagraphs().forEach(p -> reemplazar(p, variables))
                    )
                )
            );

            // Headers y footers
            doc.getHeaderList().forEach(h ->
                h.getParagraphs().forEach(p -> reemplazar(p, variables)));
            doc.getFooterList().forEach(f ->
                f.getParagraphs().forEach(p -> reemplazar(p, variables)));

            doc.write(out);
            return out.toByteArray();
        }
    }

    private void reemplazar(XWPFParagraph paragraph, Map<String, String> variables) {
        List<XWPFRun> runs = paragraph.getRuns();
        if (runs == null || runs.isEmpty()) return;

        // Reconstruye el texto completo (los marcadores pueden estar divididos en varios runs)
        StringBuilder sb = new StringBuilder();
        runs.forEach(r -> { String t = r.getText(0); if (t != null) sb.append(t); });

        String texto = sb.toString();
        boolean modificado = false;

        for (Map.Entry<String, String> entry : variables.entrySet()) {
            if (texto.contains(entry.getKey())) {
                texto = texto.replace(entry.getKey(),
                    entry.getValue() != null ? entry.getValue() : "");
                modificado = true;
            }
        }

        if (modificado) {
            // Escribe el texto final en el primer run y elimina el resto
            runs.get(0).setText(texto, 0);
            for (int i = runs.size() - 1; i >= 1; i--) {
                paragraph.removeRun(i);
            }
        }
    }

    public String fechaActual() {
        return LocalDate.now().format(FECHA_FORMATO);
    }
}