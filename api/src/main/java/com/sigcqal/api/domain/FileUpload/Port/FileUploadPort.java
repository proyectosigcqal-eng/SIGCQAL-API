package com.sigcqal.api.domain.FileUpload.Port;

public interface FileUploadPort {
    String guardarArchivo(byte[] contenido, String nombreArchivo);
    String guardarArchivoOficio(byte[] contenido, String nombreArchivo);
    String guardarArchivoExpediente(byte[] contenido, String nombreArchivo);
    String guardarArchivoConstancia(byte[] contenido, String nombreArchivo);
String guardarArchivoAmparo(byte[] contenido, String nombreArchivo); 
    String guardarArchivoQuejaAri(byte[] contenido, String nombreArchivo);
    String guardarArchivoRLCir(byte[] contenido, String nombreArchivo);
    String guardarArchivoQuejaRlCir(byte[] contenido, String nombreArchivo);
}
