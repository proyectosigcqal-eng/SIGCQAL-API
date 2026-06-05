package com.sigcqal.api.domain.ModuloAreaSustantiva.DetalleAsesoria.Model;

public interface DetalleAsesoria {
    Integer getIdExpediente();
    String getFolioGobierno();
    String getFechaSolicitud(); // O LocalDateTime si lo prefieres
    String getNombreCompleto();
    String getEstatusExpediente();
    String getNombreMunicipio();
    String getNombreActo();
    String getNombreAutoridad();
    String getEstatusDetalle();
    String getCalificacionActo();
    String getProblematica();
    String getSeguimiento();
    String getFechaNotificacion();
    String getRfc();
    String getCurp();
    String getTelefono();
    String getCorreo();
    String getNombreAsesor();
}
