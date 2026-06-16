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
    private final String carpetaDestinoOficios = "uploads/oficios/";
    private final String carpetaDestinoExpedientes = "uploads/expedientes/";
    private final String carpetaDestinoConstancias = "uploads/constancias/";

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

@Override
public String guardarArchivoOficio(byte[] contenido, String nombreArchivo) {
    try {
        Path root = Paths.get(".").toAbsolutePath().normalize();
        Path directorioDestino = root.resolve(carpetaDestinoOficios);
        if (!Files.exists(directorioDestino)) {
            Files.createDirectories(directorioDestino);
        }

        Path ficheroFinal = directorioDestino.resolve(nombreArchivo);
        Files.write(ficheroFinal, contenido);
        System.out.println("Archivo guardado en: " + ficheroFinal.toAbsolutePath());

        return "/api/files/oficios/" + nombreArchivo;
    } catch (IOException e) {
        e.printStackTrace();
        throw new FileStorageException("Error de E/S al guardar el PDF: " + e.getMessage(), e);
    }
}

@Override
public String guardarArchivoExpediente(byte[] contenido, String nombreArchivo) {
    try {
        Path root = Paths.get(".").toAbsolutePath().normalize();
        Path directorioDestino = root.resolve(carpetaDestinoExpedientes);
        if (!Files.exists(directorioDestino)) {
            Files.createDirectories(directorioDestino);
        }
        
        Path ficheroFinal = directorioDestino.resolve(nombreArchivo);
        Files.write(ficheroFinal, contenido);
        System.out.println("Archivo guardado en: " + ficheroFinal.toAbsolutePath());

        return "/api/files/expedientes/" + nombreArchivo;
    } catch (IOException e) {
        e.printStackTrace();
        throw new FileStorageException("Error al guardar documento de expediente: " + e.getMessage(), e);
    }
}

@Override
public String guardarArchivoConstancia(byte[] contenido, String nombreArchivo) {
    try {
        Path root = Paths.get(".").toAbsolutePath().normalize();
        Path directorioDestino = root.resolve(carpetaDestinoConstancias);
        if (!Files.exists(directorioDestino)) {
            Files.createDirectories(directorioDestino);
        }

        Path ficheroFinal = directorioDestino.resolve(nombreArchivo);
        Files.write(ficheroFinal, contenido);
        System.out.println("Archivo guardado en: " + ficheroFinal.toAbsolutePath());

        return "/api/files/constancias/" + nombreArchivo;
    } catch (IOException e) {
        e.printStackTrace();
        throw new FileStorageException("Error de E/S al guardar el PDF: " + e.getMessage(), e);
    }
}

public String guardarArchivoQuejaAri(byte[] contenido, String nombreArchivo) {
    try {
        Path root = Paths.get("uploads/quejas-ari").toAbsolutePath().normalize();
        java.nio.file.Files.createDirectories(root); // 🛠️ Esto evita que falle si la carpeta no existe
        
        Path fichero = root.resolve(nombreArchivo);
        java.nio.file.Files.write(fichero, contenido);
        
        // Retornamos la URL que usará el frontend para descargar el documento
        return "/api/files/quejas-ari/" + nombreArchivo; 
    } catch (IOException e) {
        e.printStackTrace(); 
        throw new RuntimeException("Error físico al escribir el archivo ARI en disco", e); // 💥 ¡Ya no devuelve null!

    }
}
}
