package com.sigcqal.api.infra.FileUpload.Adapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

import com.sigcqal.api.domain.FileUpload.Port.FileUploadPort;
import com.sigcqal.api.infra.exception.FileStorageException;

@Component
public class FileUploadAdapter implements FileUploadPort {
    
    private final String carpetaDestino = "uploads/memorandums/";

   @Override
public String guardarArchivo(byte[] contenido, String nombreArchivo) {
    try {
        Path root = Paths.get(".").toAbsolutePath().normalize();
        Path directorioDestino = root.resolve(carpetaDestino);
        if (!Files.exists(directorioDestino)) {
            Files.createDirectories(directorioDestino);
        }

        Path ficheroFinal = directorioDestino.resolve(nombreArchivo);
        Files.write(ficheroFinal, contenido);
        System.out.println("Archivo guardado en: " + ficheroFinal.toAbsolutePath());
    
        return "/api/files/memorandums/" + nombreArchivo;
    } catch (IOException e) {
        e.printStackTrace(); 
        throw new FileStorageException("Error de E/S al guardar el PDF: " + e.getMessage(), e);
    }
}
}
