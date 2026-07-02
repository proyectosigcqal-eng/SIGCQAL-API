package com.sigcqal.api.infra.ModuloAreaSustantiva.BandejaRepresentacionLegal.Mapper;

import com.sigcqal.api.domain.ModuloAreaSustantiva.BandejaRepresentacionLegal.Model.RepresentacionBandeja;
import com.sigcqal.api.web.ModuloAreaSustantiva.BandejaAsesoria.Dto.UltimaModificacionDto;
import com.sigcqal.api.web.ModuloAreaSustantiva.BandejaRepresentacionLegal.Dto.BandejaRepresentacionResponseDto;
import org.springframework.stereotype.Component;

@Component
public class BandejaRepresentacionMapper {

    /**
     * Mapea el array de la query nativa al modelo de dominio.
     *
     * ÍNDICES DE LA QUERY (deben coincidir con el SELECT en el repositorio):
     *  [0]  folio
     *  [1]  idExpediente
     *  [2]  municipioProcedencia
     *  [3]  contribuyente
     *  [4]  tipoActo
     *  [5]  estatusPrincipal
     *  [6]  estatusSecundario
     *  [7]  ultimaModificacionDescripcion
     *  [8]  ultimaModificacionTimestamp
     *  [9]  bloqueado
     *  [10] tieneFicha
     *  [11] tieneCir
     *  [12] tieneDemanda
     *  [13] tieneOficio
     *  [14] tieneAudiencia
     *  [15] tieneSentencia
     *  [16] tieneEjecutoria
     *  [17] tieneCumplimiento
     *  [18] fechaCir
     *  [19] fechaDemanda
     *  [20] fechaOficio
     *  [21] fechaAudiencia
     *  [22] fechaSentencia
     *  [23] fechaEjecutoria
     *  [24] fechaRegistro
     *  [25] idDemandaAmparo
     *  [26] semaforo
     *  [27] idRepresentacionLegal   ← NUEVO
     *  [28] idRlCir                 ← NUEVO
     *  [29] idQuejaRlCir            ← NUEVO
     *  [30] esEvolucion             ← NUEVO
     *
     * Si tu query aún no tiene las posiciones 27-30, agrégalas al SELECT
     * (ver comentario al final de este archivo).
     */
    public RepresentacionBandeja toDomain(Object[] row) {
        return RepresentacionBandeja.builder()
            .folio(asString(row[0]))
            .idExpediente(asString(row[1]))
            .municipioProcedencia(asString(row[2]))
            .contribuyente(asString(row[3]))
            .tipoActo(asString(row[4]))
            .estatusPrincipal(asString(row[5]))
            .estatusSecundario(asString(row[6]))
            .ultimaModificacionDescripcion(asString(row[7]))
            .ultimaModificacionTimestamp(asString(row[8]))
            .bloqueado(asBoolean(row[9]))
            .tieneFicha(asBoolean(row[10]))
            .tieneCir(asBoolean(row[11]))
            .tieneDemanda(asBoolean(row[12]))
            .tieneOficio(asBoolean(row[13]))
            .tieneAudiencia(asBoolean(row[14]))
            .tieneSentencia(asBoolean(row[15]))
            .tieneEjecutoria(asBoolean(row[16]))
            .tieneCumplimiento(asBoolean(row[17]))
            .fechaCir(asString(row[18]))
            .fechaDemanda(asString(row[19]))
            .fechaOficio(asString(row[20]))
            .fechaAudiencia(asString(row[21]))
            .fechaSentencia(asString(row[22]))
            .fechaEjecutoria(asString(row[23]))
            .fechaRegistro(asString(row[24]))
            .idDemandaAmparo(asInteger(row[25]))
            .semaforo(asString(row[26]))
            // ★ NUEVOS — posiciones 27-30 (agregar al SELECT de la query):
            .idRepresentacionLegal(row.length > 27 ? asInteger(row[27]) : null)
            .idRlCir(row.length > 28          ? asInteger(row[28]) : null)
            .idQuejaRlCir(row.length > 29     ? asInteger(row[29]) : null)
            .esEvolucion(row.length > 30      ? asBoolean(row[30]) : null)
            .build();
    }

    public BandejaRepresentacionResponseDto toDto(RepresentacionBandeja item) {
        UltimaModificacionDto ultimaModificacion = UltimaModificacionDto.builder()
            .descripcion(item.getUltimaModificacionDescripcion())
            .timestamp(item.getUltimaModificacionTimestamp())
            .build();

        return BandejaRepresentacionResponseDto.builder()
            // ── Identificadores ───────────────────────────────────────────────
            .idRepresentacionLegal(item.getIdRepresentacionLegal())
            .idExpediente(asIntegerFromString(item.getIdExpediente()))

            // ── Datos del expediente ──────────────────────────────────────────
            // ★ folioGobierno mapea desde folio (el frontend busca 'folioGobierno')
            .folioGobierno(item.getFolio())
            .contribuyente(item.getContribuyente())
            .municipio(item.getMunicipioProcedencia())
            // ★ estatus mapea desde estatusPrincipal (el frontend busca 'estatus')
            .estatus(item.getEstatusPrincipal())
            .estatusSecundario(item.getEstatusSecundario())
            .esEvolucion(item.getEsEvolucion())     // ← NUEVO
            .fechaRegistro(item.getFechaRegistro())
            .semaforo(item.getSemaforo())
            .ultimaModificacion(ultimaModificacion)

            // ── IDs de hitos ──────────────────────────────────────────────────
            .idRlCir(item.getIdRlCir())             // ← NUEVO
            .idQuejaRlCir(item.getIdQuejaRlCir())   // ← NUEVO
            .idDemandaAmparo(item.getIdDemandaAmparo())

            // ── Flags de hitos ────────────────────────────────────────────────
            .tieneFicha(item.getTieneFicha())
            .tieneCir(item.getTieneCir())
            .tieneDemanda(item.getTieneDemanda())
            .tieneOficio(item.getTieneOficio())
            .tieneAudiencia(item.getTieneAudiencia())
            .tieneSentencia(item.getTieneSentencia())
            .tieneEjecutoria(item.getTieneEjecutoria())
            .tieneCumplimiento(item.getTieneCumplimiento())

            // ── Fechas de hitos ───────────────────────────────────────────────
            .fechaCir(item.getFechaCir())
            .fechaDemanda(item.getFechaDemanda())
            .fechaOficio(item.getFechaOficio())
            .fechaAudiencia(item.getFechaAudiencia())
            .fechaSentencia(item.getFechaSentencia())
            .fechaEjecutoria(item.getFechaEjecutoria())
            .build();
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private String asString(Object value) {
        return value != null ? value.toString() : null;
    }

    private Boolean asBoolean(Object value) {
        if (value == null) return false;
        if (value instanceof Boolean b) return b;
        return Boolean.parseBoolean(value.toString());
    }

    private Integer asInteger(Object value) {
        if (value == null) return null;
        if (value instanceof Integer i) return i;
        if (value instanceof Number n) return n.intValue();
        try { return Integer.parseInt(value.toString()); }
        catch (NumberFormatException e) { return null; }
    }

    private Integer asIntegerFromString(String value) {
        if (value == null) return null;
        try { return Integer.parseInt(value); }
        catch (NumberFormatException e) { return null; }
    }
}

/*
 * ─────────────────────────────────────────────────────────────────────────────
 * CAMBIO REQUERIDO EN LA QUERY NATIVA DEL REPOSITORIO
 * ─────────────────────────────────────────────────────────────────────────────
 * Agrega estas 4 columnas al final del SELECT de tu @Query existente
 * (ajusta nombres de tabla/schema según los tuyos):
 *
 *   rl.id                                          AS idRepresentacionLegal,  -- [27]
 *   cir.id                                         AS idRlCir,                -- [28]
 *   qcir.id                                        AS idQuejaRlCir,           -- [29]
 *   rl.es_evolucion                                AS esEvolucion             -- [30]
 *
 * Y los LEFT JOINs correspondientes si no los tienes ya:
 *
 *   LEFT JOIN sustantiva.rl_cir      cir  ON cir.id_expediente  = rl.id_expediente
 *   LEFT JOIN sustantiva.queja_rl_cir qcir ON qcir.id_expediente = rl.id_expediente
 *
 * ─────────────────────────────────────────────────────────────────────────────
 */