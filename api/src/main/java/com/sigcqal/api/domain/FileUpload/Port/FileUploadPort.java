package com.sigcqal.api.domain.FileUpload.Port;

public interface FileUploadPort {
    String guardarArchivo(byte[] contenido, String nombreArchivo);
}
