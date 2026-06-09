package com.sigcqal.api.web.ModuloAreaSustantiva.DetalleAsesoria.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetalleAsesoriaResponseDTO {

    @JsonProperty("id_expediente")
    private Integer idExpediente;

    private String folio;

    @JsonProperty("fecha_registro")
    private String fechaRegistro;

    private String contribuyente;

    @JsonProperty("folio_asesoria")
    private String folioAsesoria;

    @JsonProperty("autoridad_responsable")
    private String autoridadResponsable;

    @JsonProperty("descripcion_sintetica")
    private String descripcionSintetica;

    @JsonProperty("estatus_actual")
    private String estatusActual;

    @JsonProperty("progreso_porcentaje")
    private Integer progresoPorcentaje;

    @JsonProperty("analisis_legal")
    private AnalisisLegalDTO analisisLegal;

    private BitacoraDTO bitacora;

    // ── Objetos anidados ──────────────────────────────────────────────────

    @Data
    @Builder
    public static class AnalisisLegalDTO {
        @JsonProperty("clasificacion_atencion")
        private String clasificacionAtencion;

        @JsonProperty("autoridad_fiscal_emisora")
        private String autoridadFiscalEmisora;

        @JsonProperty("tipo_acto_impuesto")
        private String tipoActoImpuesto;

        @JsonProperty("estatus_expediente")
        private String estatusExpediente;

        @JsonProperty("fundamento_analisis_juridico")
        private String fundamentoAnalisisJuridico;
    }

    @Data
    @Builder
    public static class BitacoraDTO {
        private EventoBitacoraDTO registro;
        private EventoBitacoraDTO calificacion;
        private EventoBitacoraDTO conclusion;
    }

    @Data
    @Builder
    public static class EventoBitacoraDTO {
        private String descripcion;
        private String timestamp;
        private String usuario;
        private String adjunto;

        @JsonProperty("datosContribuyente")
        private java.util.Map<String, String> datosContribuyente;
    }
}
