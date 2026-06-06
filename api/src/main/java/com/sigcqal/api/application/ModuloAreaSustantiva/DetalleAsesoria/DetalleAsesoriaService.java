package com.sigcqal.api.application.ModuloAreaSustantiva.DetalleAsesoria;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.sigcqal.api.domain.ModuloAreaSustantiva.DetalleAsesoria.Model.DetalleAsesoria;
import com.sigcqal.api.infra.ModuloAreaSustantiva.DetalleAsesoria.Repository.DetalleAsesoriaRepository;
import com.sigcqal.api.web.ModuloAreaSustantiva.DetalleAsesoria.Dto.DetalleAsesoriaResponseDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.DetalleAsesoria.Dto.DetalleAsesoriaResponseDTO.AnalisisLegalDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.DetalleAsesoria.Dto.DetalleAsesoriaResponseDTO.BitacoraDTO;
import com.sigcqal.api.web.ModuloAreaSustantiva.DetalleAsesoria.Dto.DetalleAsesoriaResponseDTO.EventoBitacoraDTO;

import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DetalleAsesoriaService {

    private final DetalleAsesoriaRepository repository;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FMT_TS = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public DetalleAsesoriaResponseDTO obtenerDetalle(String folio) {

        DetalleAsesoria projection = repository.findDetalleByFolio(folio)
                .orElseThrow(() -> new RuntimeException("Expediente no encontrado con folio: " + folio));

        String nombreCompleto = str(projection.getNombreCompleto());
        String estatusExp = str(projection.getEstatusExpediente());

        // ── Progreso según estatus ──────────────────────────────────────
        int progreso = calcularProgreso(estatusExp, projection.getFechaNotificacion() != null);

        // ── Análisis Legal ──────────────────────────────────────────────
        AnalisisLegalDTO analisis = AnalisisLegalDTO.builder()
                .clasificacionAtencion("ASESORÍA SIMPLIFICADA")
                .autoridadFiscalEmisora(str(projection.getNombreAutoridad()))
                .tipoActoImpuesto(str(projection.getNombreActo()))
                .estatusExpediente(str(projection.getEstatusDetalle()))
                .fundamentoAnalisisJuridico(str(projection.getCalificacionActo()))
                .build();

        // ── Bitácora ────────────────────────────────────────────────────
        
        // Evento 1: Registro (siempre existe si hay expediente)
        Map<String, String> datosContribuyente = new LinkedHashMap<>();
        datosContribuyente.put("Nombre", nombreCompleto);
        datosContribuyente.put("RFC", str(projection.getRfc()));
        datosContribuyente.put("CURP", str(projection.getCurp()));
        datosContribuyente.put("Teléfono", str(projection.getTelefono()));
        datosContribuyente.put("Correo", str(projection.getCorreo()));

        EventoBitacoraDTO registro = EventoBitacoraDTO.builder()
                .descripcion("Folio generado exitosamente: " + folio)
                .timestamp(projection.getFechaSolicitud() != null 
                    ? toTimestamp(projection.getFechaSolicitud().toString()) : "")
                .usuario("Capturista SIGCQAL")
                .adjunto(null)
                .datosContribuyente(datosContribuyente)
                .build();

        // Evento 2: Calificación (existe si hay detalle_asesoria)
        EventoBitacoraDTO calificacion = null;
        if (projection.getCalificacionActo() != null) {
            calificacion = EventoBitacoraDTO.builder()
                    .descripcion(str(projection.getCalificacionActo()))
                    .timestamp(projection.getFechaNotificacion() != null 
                        ? toTimestamp(projection.getFechaNotificacion().toString()) : "")
                    .usuario(str(projection.getNombreAsesor()))
                    .adjunto(null)
                    .datosContribuyente(null)
                    .build();
        }

        // Evento 3: Conclusión (existe si hay seguimiento)
        EventoBitacoraDTO conclusion = null;
        if (projection.getSeguimiento() != null) {
            conclusion = EventoBitacoraDTO.builder()
                    .descripcion(str(projection.getSeguimiento()))
                    .timestamp(projection.getFechaNotificacion() != null 
                        ? toTimestamp(projection.getFechaNotificacion().toString()) : "")
                    .usuario(str(projection.getNombreAsesor()))
                    .adjunto(null)
                    .datosContribuyente(null)
                    .build();
        }

        BitacoraDTO bitacora = BitacoraDTO.builder()
                .registro(registro)
                .calificacion(calificacion)
                .conclusion(conclusion)
                .build();

        return DetalleAsesoriaResponseDTO.builder()
                .folio(folio)
                .fechaRegistro(projection.getFechaSolicitud() != null 
                    ? toFecha(projection.getFechaSolicitud().toString()) : "")
                .idExpediente(projection.getIdExpediente() != null ? Long.valueOf(projection.getIdExpediente()) : null)
                .contribuyente(nombreCompleto)
                .estatusActual(estatusExp)
                .progresoPorcentaje(progreso)
                .analisisLegal(analisis)
                .bitacora(bitacora)
                .build();
    }

    private String str(Object o) {
        return o != null ? o.toString() : "";
    }

    private String toFecha(String ts) {
        try { 
            return ts.substring(0, 10).replace("-", "/"); 
        } catch (Exception e) { 
            return ts; 
        }
    }

    private String toTimestamp(String ts) {
        try { 
            return ts.substring(0, 16).replace("T", " "); 
        } catch (Exception e) { 
            return ts; 
        }
    }

    private int calcularProgreso(String estatus, boolean tieneCalificacion) {
        if (estatus == null) return 0;
        if (tieneCalificacion) return 66;
        return 33;
    }
}