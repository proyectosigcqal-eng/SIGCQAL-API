// PlantillaController.java
package com.sigcqal.api.infra.ModuloCorrespondencia.Plantilla;

import com.sigcqal.api.application.ModuloCorrespondencia.Documento.GeneradorDocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/plantillas")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PlantillaController {

    private final GeneradorDocumentoService generadorDocumentoService;

    private static final Map<String, String[]> PLANTILLAS_PERMITIDAS = Map.of(
        "amparo_impuesto",                    new String[]{"{{NOMBRE_CONTRIBUYENTE}}"},
        "carta_compromiso_representacion_legal", new String[]{"{{NOMBRE_CONTRIBUYENTE}}"},
        "informe_terminacion_dictamen",        new String[]{"{{NOMBRE_CONTRIBUYENTE}}", "{{NOMBRE_ENCARGADO}}", "{{NOMBRE_ASESOR}}"},
        "informe_terminacion_servicio",        new String[]{"{{NOMBRE_CONTRIBUYENTE}}", "{{NOMBRE_ENCARGADO}}", "{{NOMBRE_ASESOR}}"},
        "servicio_representacion_legal",       new String[]{"{{NOMBRE_CONTRIBUYENTE}}"},
        "solicitud_servicio_asesoria",         new String[]{"{{NOMBRE_CONTRIBUYENTE}}"}
    );

    @PostMapping("/generar/{nombrePlantilla}")
    public ResponseEntity<byte[]> generarPlantilla(
            @PathVariable String nombrePlantilla,
            @RequestBody Map<String, String> variables) {

        if (!PLANTILLAS_PERMITIDAS.containsKey(nombrePlantilla)) {
            return ResponseEntity.badRequest().build();
        }

        try {
            // Convertir variables del request a marcadores {{CLAVE}} → valor
            Map<String, String> marcadores = new java.util.HashMap<>();
            variables.forEach((k, v) ->
                marcadores.put("{{" + k + "}}", v != null ? v : "")
            );

            byte[] docx = generadorDocumentoService
                .generarDesPlantilla("plantilla_" + nombrePlantilla + ".docx", marcadores);

            String nombreArchivo = nombrePlantilla + ".docx";

            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + nombreArchivo + "\"")
                .body(docx);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}