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

    @GetMapping("/memorandums/{nombre}")
    public ResponseEntity<Resource> descargarMemorandum(@PathVariable String nombre) {
        try {
            Path root = Paths.get(".").toAbsolutePath().normalize();
            Path archivo = root.resolve("uploads/memorandums/" + nombre);

            System.out.println(">>> Directorio de trabajo: " + root);
            System.out.println(">>> Buscando archivo en: " + archivo);

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
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                );
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

    @GetMapping("/oficios/{nombre}")
    public ResponseEntity<Resource> descargarOficio(@PathVariable String nombre) {
        try {
            Path root = Paths.get(".").toAbsolutePath().normalize();
            Path archivo = root.resolve("uploads/oficios/" + nombre);

            System.out.println(">>> Directorio de trabajo: " + root);
            System.out.println(">>> Buscando archivo en: " + archivo);

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
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                );
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

    @GetMapping("/expedientes/{nombre}")
    public ResponseEntity<Resource> descargarExpediente(@PathVariable String nombre) {
        try {
            Path root = Paths.get(".").toAbsolutePath().normalize();
            Path archivo = root.resolve("uploads/expedientes/" + nombre);

            Resource resource = new UrlResource(archivo.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            MediaType contentType;
            if (nombre.endsWith(".pdf")) {
                contentType = MediaType.APPLICATION_PDF;
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

    @GetMapping("/constancias/{nombre}")
    public ResponseEntity<Resource> descargarConstancia(@PathVariable String nombre) {
        try {
            Path root = Paths.get(".").toAbsolutePath().normalize();
            Path archivo = root.resolve("uploads/constancias/" + nombre);

            Resource resource = new UrlResource(archivo.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            MediaType contentType;
            if (nombre.endsWith(".pdf")) {
                contentType = MediaType.APPLICATION_PDF;
            } else if (nombre.endsWith(".docx")) {
                contentType = MediaType.parseMediaType(
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                );
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



@GetMapping("/quejas-ari/{nombre}")
public ResponseEntity<Resource> descargarQuejaAri(@PathVariable String nombre) {
    try {
        Path root = Paths.get(".").toAbsolutePath().normalize();
        Path archivo = root.resolve("uploads/quejas-ari/" + nombre);
        Resource resource = new UrlResource(archivo.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        MediaType contentType = nombre.endsWith(".pdf")
            ? MediaType.APPLICATION_PDF
            : MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");

        String disposition = nombre.endsWith(".pdf") ? "inline" : "attachment";

        return ResponseEntity.ok()
            .contentType(contentType)
            .header(HttpHeaders.CONTENT_DISPOSITION, disposition + "; filename=\"" + nombre + "\"")
            .body(resource);
    } catch (MalformedURLException e) {
        return ResponseEntity.badRequest().build();
    }
}

@GetMapping("/amparo/{nombre}")
public ResponseEntity<Resource> descargarAmparo(@PathVariable String nombre) {
    try {
        Path root = Paths.get(".").toAbsolutePath().normalize();
        Path archivo = root.resolve("uploads/amparo/" + nombre);
        Resource resource = new UrlResource(archivo.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        MediaType contentType = nombre.endsWith(".pdf")
            ? MediaType.APPLICATION_PDF
            : MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document");

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
}

