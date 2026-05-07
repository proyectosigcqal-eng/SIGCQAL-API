package com.sigcqal.api.web.ModuloCorrespondencia.FileUpload;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "*")
public class FileDownloadController {

    private final String carpetaBase = "uploads/";

    @GetMapping("/memorandums/{nombre}")
    public ResponseEntity<Resource> descargarMemorandum(@PathVariable String nombre) {
        return servirArchivo("memorandums/" + nombre, nombre);
    }

    private ResponseEntity<Resource> servirArchivo(String ruta, String nombre) {
        try {
            Path root = Paths.get(".").toAbsolutePath().normalize();
            Path archivo = root.resolve("uploads/" + ruta);
            Resource resource = new UrlResource(archivo.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            String contentType = nombre.endsWith(".docx")
                ? "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                : "application/octet-stream";

            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + nombre + "\"")
                .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}