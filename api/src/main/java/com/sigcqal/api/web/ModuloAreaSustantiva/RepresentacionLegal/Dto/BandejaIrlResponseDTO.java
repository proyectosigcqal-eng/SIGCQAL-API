package com.sigcqal.api.web.ModuloAreaSustantiva.RepresentacionLegal.Dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class BandejaIrlResponseDTO {

    // ── Campos originales ────────────────────────────────────────────────────
    private Integer       id;
    private String        folioGobierno;
    private String        contribuyente;
    private String        asesor;
    private String        municipio;
    private String        estatus;
    private Integer       idEstatus;
    private Integer       diasRestantes;
    private LocalDateTime fechaCreacion;
    private Boolean       esEvolucion;

    // ── IDs de hitos — necesarios para los botones de descarga ───────────────
    // Sin estos, los if(idRlCir) / if(idDemandaAmparo) del frontend nunca entran

    /** ID del registro en rl_cir — habilita botón DESCARGAR CIR */
    private Integer idRepresentacionLegal;

    /** ID del expediente — necesario para navegar al formulario */
    private Integer idExpediente;

    /** ID del CIR generado — habilita botón DESCARGAR CIR */
    private Integer idRlCir;

    /** ID del CIR queja — habilita botón DESCARGAR CIR QUEJA */
    private Integer idQuejaRlCir;

    /** ID de la demanda de amparo — habilita botón DESCARGAR DEMANDA y semáforo */
    private Integer idDemandaAmparo;

    // ── Flags de hitos — necesarios para botones de modal/detalle ────────────
    // El frontend muestra "DATOS AUDIENCIA", "DATOS SENTENCIA", etc.
    // solo cuando estos son true

    /** true si ya existe un CIR generado para este expediente */
    private Boolean tieneCir;

    /** true si ya existe una demanda de amparo registrada */
    private Boolean tieneDemanda;

    /** true si ya existe una audiencia registrada */
    private Boolean tieneAudiencia;

    /** true si ya existe una sentencia registrada */
    private Boolean tieneSentencia;

    /** true si ya existe una sentencia ejecutoria registrada */
    private Boolean tieneEjecutoria;

    /** true si ya existe un cumplimiento notificado */
    private Boolean tieneCumplimiento;

    // ── Fecha de registro (alias de fechaCreacion para el frontend) ───────────
    // El service de bandeja lee tanto fechaRegistro como fechaCreacion
    private LocalDateTime fechaRegistro;
}