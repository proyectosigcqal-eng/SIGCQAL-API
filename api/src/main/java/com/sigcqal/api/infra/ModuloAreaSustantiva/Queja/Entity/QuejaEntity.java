package com.sigcqal.api.infra.ModuloAreaSustantiva.Queja.Entity;

import java.time.LocalDateTime;
import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import com.sigcqal.api.infra.Catalogo.Asesor.Entity.AsesorEntity;
// Nota: Ajusta los imports de Autoridad y EstatusQueja según tus paquetes de catálogos reales
import com.sigcqal.api.infra.Catalogo.Autoridad.Entity.AutoridadEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @ManyToOne
    @JoinColumn(name = "id_expediente")
    private ExpedienteEntity expediente;

    @ManyToOne
    @JoinColumn(name = "id_asesor")
    private AsesorEntity asesor;

    @ManyToOne
    @JoinColumn(name = "id_autoridad")
    private AutoridadEntity autoridad;

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