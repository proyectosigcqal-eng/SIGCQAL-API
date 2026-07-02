package com.sigcqal.api.web.ModuloAreaSustantiva.BandejaRepresentacionLegal.Dto;

import com.sigcqal.api.web.ModuloAreaSustantiva.BandejaAsesoria.Dto.UltimaModificacionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de bandeja IRL.
 *
 * Los nombres de campo deben coincidir EXACTAMENTE con lo que lee adaptarItem()
 * en bandejaIrlService.js. Si un campo llega null, el botón correspondiente
 * no aparece en el frontend.
 *
 * Mapa campo → botón que habilita:
 *   idRlCir          → DESCARGAR CIR
 *   idQuejaRlCir     → DESCARGAR CIR QUEJA
 *   idDemandaAmparo  → DESCARGAR DEMANDA + semáforo judicial
 *   tieneCir         → (flag derivado de idRlCir)
 *   tieneDemanda     → label VER DEMANDA vs REGISTRAR DEMANDA
 *   tieneAudiencia   → botón DATOS AUDIENCIA
 *   tieneSentencia   → botón DATOS SENTENCIA
 *   tieneEjecutoria  → botón DATOS EJECUTORIA
 *   tieneCumplimiento → botón DATOS CUMPLIMIENTO
 *   esEvolucion      → filtro IRL Directa vs IRL Evolución
 *   folioGobierno    → folio mostrado en tabla y usado en rutas de navegación
 *   estatus          → lógica de calcularAccionesIrl
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BandejaRepresentacionResponseDto {

    // ── Identificadores ──────────────────────────────────────────────────────
    /** id de representacion_legal — usado como key de React y en rutas */
    private Integer idRepresentacionLegal;

    /** id del expediente — usado en navegación */
    private Integer idExpediente;  // ojo: era String en el dominio, se convierte aquí

    // ── Datos del expediente ─────────────────────────────────────────────────
    /**
     * folioGobierno — el frontend lo busca por ESTE nombre exacto en adaptarItem:
     *   obtenerValor(item, ['folio', 'folioGobierno', ...])
     * Mapear desde domain.getFolio()
     */
    private String folioGobierno;

    private String contribuyente;
    private String asesor;
    private String municipio;          // frontend busca 'municipio' o 'municipioProcedencia'

    /**
     * estatus — el frontend busca 'estatus' o 'estatusPrincipal'.
     * calcularAccionesIrl() hace estUp = item.estatus.toUpperCase()
     * Mapear desde domain.getEstatusPrincipal()
     */
    private String estatus;

    private String  estatusSecundario;
    private Boolean esEvolucion;       // ← NUEVO: filtra IRL Directa vs Evolución
    private String  fechaRegistro;     // frontend busca fechaRegistro o fechaCreacion
    private String  semaforo;

    // ── IDs de hitos para botones de descarga ────────────────────────────────
    /** ID del CIR generado — if(idRlCir) habilita DESCARGAR CIR */
    private Integer idRlCir;

    /** ID del CIR queja — if(idQuejaRlCir) habilita DESCARGAR CIR QUEJA */
    private Integer idQuejaRlCir;

    /** ID de la demanda de amparo — if(idDemandaAmparo) habilita DESCARGAR DEMANDA */
    private Integer idDemandaAmparo;

    // ── Flags de hitos para botones de modal/detalle ─────────────────────────
    private Boolean tieneFicha;
    private Boolean tieneCir;
    private Boolean tieneDemanda;
    private Boolean tieneOficio;
    private Boolean tieneAudiencia;
    private Boolean tieneSentencia;
    private Boolean tieneEjecutoria;
    private Boolean tieneCumplimiento;

    // ── Fechas de hitos ───────────────────────────────────────────────────────
    private String fechaCir;
    private String fechaDemanda;
    private String fechaOficio;
    private String fechaAudiencia;
    private String fechaSentencia;
    private String fechaEjecutoria;

    // ── Última modificación (estructura anidada) ──────────────────────────────
    private UltimaModificacionDto ultimaModificacion;
}