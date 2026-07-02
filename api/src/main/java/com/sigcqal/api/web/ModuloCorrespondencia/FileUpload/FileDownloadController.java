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

    // ── Helper para evitar repetición ──────────────────────────────────────
    private ResponseEntity<Resource> servirArchivo(Path archivo, String nombre) {
        try {
            Resource resource = new UrlResource(archivo.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                System.out.println(">>> Archivo NO encontrado: " + archivo);
                return ResponseEntity.notFound().build();
            }

            MediaType contentType;
            if (nombre.endsWith(".pdf")) {
                contentType = MediaType.APPLICATION_PDF;
            } else if (nombre.endsWith(".docx")) {
                contentType = MediaType.parseMediaType(
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            } else {
                contentType = MediaType.APPLICATION_OCTET_STREAM;
            }

            String disposition = nombre.endsWith(".pdf") ? "inline" : "attachment";

            return ResponseEntity.ok()
                .contentType(contentType)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                    disposition + "; filename=\"" + nombre + "\"")
                .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ── Endpoints ──────────────────────────────────────────────────────────

    @GetMapping("/memorandums/{nombre}")
    public ResponseEntity<Resource> descargarMemorandum(@PathVariable String nombre) {
        Path root = Paths.get(".").toAbsolutePath().normalize();
        System.out.println(">>> Directorio de trabajo: " + root);
        return servirArchivo(root.resolve("uploads/memorandums/" + nombre), nombre);
    }

    @GetMapping("/seguimiento-memorandum/{nombre}")
    public ResponseEntity<Resource> descargarSeguimientoMemorandum(@PathVariable String nombre) {
        Path root = Paths.get(".").toAbsolutePath().normalize();
        return servirArchivo(root.resolve("uploads/seguimiento-memorandum/" + nombre), nombre);
    }

    @GetMapping("/oficios/{nombre}")
    public ResponseEntity<Resource> descargarOficio(@PathVariable String nombre) {
        Path root = Paths.get(".").toAbsolutePath().normalize();
        System.out.println(">>> Directorio de trabajo: " + root);
        return servirArchivo(root.resolve("uploads/oficios/" + nombre), nombre);
    }

    @GetMapping("/expedientes/{nombre}")
    public ResponseEntity<Resource> descargarExpediente(@PathVariable String nombre) {
        Path root = Paths.get(".").toAbsolutePath().normalize();
        return servirArchivo(root.resolve("uploads/expedientes/" + nombre), nombre);
    }

    @GetMapping("/constancias/{nombre}")
    public ResponseEntity<Resource> descargarConstancia(@PathVariable String nombre) {
        Path root = Paths.get(".").toAbsolutePath().normalize();
        return servirArchivo(root.resolve("uploads/constancias/" + nombre), nombre);
    }

    @GetMapping("/quejas-ari/{nombre}")
    public ResponseEntity<Resource> descargarQuejaAri(@PathVariable String nombre) {
        Path root = Paths.get(".").toAbsolutePath().normalize();
        return servirArchivo(root.resolve("uploads/quejas-ari/" + nombre), nombre);
    }

    @GetMapping("/amparo/{nombre}")
    public ResponseEntity<Resource> descargarAmparo(@PathVariable String nombre) {
        Path root = Paths.get(".").toAbsolutePath().normalize();
        return servirArchivo(root.resolve("uploads/amparo/" + nombre), nombre);
    }

    @GetMapping("/queja-rl-cir/{nombre}")
    public ResponseEntity<Resource> descargarQuejaRlCir(@PathVariable String nombre) {
        Path root = Paths.get(".").toAbsolutePath().normalize();
        System.out.println(">>> Buscando RL_CIR en: " + root.resolve("uploads/queja-rl-cir/" + nombre));
        return servirArchivo(root.resolve("uploads/queja-rl-cir/" + nombre), nombre);
    }

    @GetMapping("/RLCir/{nombre}")
    public ResponseEntity<Resource> descargarRLCir(@PathVariable String nombre) {
        Path root = Paths.get(".").toAbsolutePath().normalize();
        System.out.println(">>> Buscando RLCir en: " + root.resolve("uploads/RLCir/" + nombre));
        return servirArchivo(root.resolve("uploads/RLCir/" + nombre), nombre);
    }
}