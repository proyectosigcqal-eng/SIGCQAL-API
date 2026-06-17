package com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Entity;

import java.time.LocalDateTime;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import com.sigcqal.api.infra.Catalogo.Asesor.Entity.AsesorEntity;
import com.sigcqal.api.infra.Catalogo.Autoridad.Entity.AutoridadEntity;
import com.sigcqal.api.infra.Catalogo.DetalleAsesoria.Entity.DetalleAsesoriaEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "quejas", schema = "sustantiva")
@Data
public class QuejaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_queja")
    private Integer idQueja;

    // MODIFICADO: Eager para asegurar la extracción del expediente y sus personas asociadas
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_expediente")
    private ExpedienteEntity expediente;

    // MODIFICADO: Eager para asegurar la extracción del asesor y su información personal
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_asesor")
    private AsesorEntity asesor;

     @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_detalle_asesoria")
    private DetalleAsesoriaEntity detalleAsesoria;

    @Column(name = "id_estatus_queja")
    private Long estatusQueja;

    @Column(name = "requisito_identificacion")
    private Boolean requisitoIdentificacion;

    @Column(name = "requisito_actos_fiscales")
    private Boolean requisitoActosFiscales;

    @Column(name = "requisito_narrativa_clara")
    private Boolean requisitoNarrativaClara;

    @Column(name = "requisito_competencia_cedecon")
    private Boolean requisitoCompetenciaCedecon;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;
}