package com.sigcqal.api.infra.ModuloAreaSustantiva.IrlDemandaAmparo.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "irl_demanda_amparo", schema = "sustantiva")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class IrlDemandaAmparoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demanda_amparo")
    private Integer idDemandaAmparo;

    @Column(name = "id_expediente", nullable = false)
    private Integer idExpediente;

            @Column(name = "id_rl_cir")
        private Integer idRlCir;

        @Column(name = "id_queja_rl_cir")
        private Integer idQuejaRlCir;

    @Column(name = "id_representacion_legal", nullable = false)
    private Integer idRepresentacionLegal;

    @Column(name = "autoridad_reclamada_municipio", length = 200)
    private String autoridadReclamadaMunicipio;

    @Column(name = "superficie_terreno", precision = 10, scale = 2)
    private BigDecimal superficieTerreno;

    @Column(name = "superficie_construccion", precision = 10, scale = 2)
    private BigDecimal superficieConstruccion;

    @Column(name = "tipo_construccion", length = 10)
    private String tipoConstruccion;

    @Column(name = "zonificacion", length = 50)
    private String zonificacion;

    @Column(name = "folio_recibo_pago", length = 100)
    private String folioReciboPago;

    @Column(name = "monto_pago", precision = 12, scale = 2)
    private BigDecimal montoPago;

    @Column(name = "fecha_primer_pago")
    private LocalDate fechaPrimerPago;

    @Column(name = "incluye_multas_historicas")
    private Boolean incluyeMultasHistoricas = false;

    @Column(name = "anios_multas_historicas", length = 200)
    private String aniosMultasHistoricas;

    @Column(name = "argumentacion_falta_notificacion", columnDefinition = "TEXT")
    private String argumentacionFaltaNotificacion;

    @Column(name = "transcripcion_ley_ingresos", columnDefinition = "TEXT")
    private String transcripcionLeyIngresos;

    @Column(name = "ruta_pdf_demanda_generada", length = 500)
    private String rutaPdfDemandaGenerada;

    @Column(name = "fecha_generacion_demanda")
    private LocalDateTime fechaGeneracionDemanda;

    @Column(name = "ruta_pdf_demanda_presentada", length = 500)
    private String rutaPdfDemandaPresentada;

    @Column(name = "ruta_pdf_acuse_demanda", length = 500)
    private String rutaPdfAcuseDemanda;

    @Column(name = "fecha_presentacion_demanda")
    private LocalDate fechaPresentacionDemanda;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;

    @Column(name = "folio_recibo_pago_2",  length = 100)
private String folioReciboPago2;

@Column(name = "num_recibo_1",  length = 100)
private String numRecibo1;

@Column(name = "num_recibo_2",  length = 100)
private String numRecibo2;

@Column(name = "clave_predial", length = 100)
private String clavePredial;

@Column(name = "num_cuenta",    length = 100)
private String numCuenta;

@Column(name = "domicilio_autoridad", length = 500)
private String domicilioAutoridad;
}