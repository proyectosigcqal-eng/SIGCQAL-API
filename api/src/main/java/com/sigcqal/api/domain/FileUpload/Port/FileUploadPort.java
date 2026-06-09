package com.sigcqal.api.domain.FileUpload.Port;

public interface FileUploadPort {
    String guardarArchivo(byte[] contenido, String nombreArchivo);
    String guardarArchivoOficio(byte[] contenido, String nombreArchivo);
    String guardarArchivoExpediente(byte[] contenido, String nombreArchivo);
    String guardarArchivoConstancia(byte[] contenido, String nombreArchivo);
}
