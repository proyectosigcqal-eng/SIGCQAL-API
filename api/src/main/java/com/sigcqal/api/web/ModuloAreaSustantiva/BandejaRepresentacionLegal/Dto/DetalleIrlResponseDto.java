package com.sigcqal.api.web.ModuloAreaSustantiva.BandejaRepresentacionLegal.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetalleIrlResponseDto {

    // ── Audiencia en Espera ──────────────────────────────────────────────
    private String numeroOficioAdmision;
    private String fechaNotificacionOficio;
    private String fechaHoraAudienciaProg;
    private String observacionesEspera;
    private String rutaPdfOficioEspera;

    // ── Audiencia Celebrada ──────────────────────────────────────────────
    private String fechaHoraCelebracion;
    private String numeroOficioActa;
    private String salaOModalidad;
    private String resultadoAudiencia;
    private Boolean asistioAutoridad;
    private String rutaPdfOficioCelebrada;

    // ── Sentencia Dictada ────────────────────────────────────────────────
    private String fechaDictado;
    private String fechaNotificacionSentencia;
    private String sentidoFallo;
    private String puntosResolutivos;
    private String numeroOficioSentencia;
    private String rutaArchivoSentencia;
    private String rutaPdfOficioSentencia;

    // ── Sentencia Ejecutoria ─────────────────────────────────────────────
    private String numeroOficioEjecutoria;
    private String fechaDeclaracionEjecutoria;
    private String requerimientoCumplimiento;
    private String rutaPdfOficioEjecutoria;

    // ── Notificación Sentencia Cumplida ──────────────────────────────────
    private String numeroOficioCumplimiento;
    private String numeroOficioArchivo;
    private String fechaNotificacionArchivo;
    private String observacionesFinales;
    private String rutaPdfOficioCumplimiento;
}