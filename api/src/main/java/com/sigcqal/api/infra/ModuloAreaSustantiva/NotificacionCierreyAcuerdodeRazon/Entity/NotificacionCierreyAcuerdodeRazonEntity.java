package com.sigcqal.api.infra.ModuloAreaSustantiva.NotificacionCierreyAcuerdodeRazon.Entity;

import com.sigcqal.api.infra.ModuloAreaSustantiva.Expediente.Entity.ExpedienteEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "cierre_expediente", schema = "sustantiva")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionCierreyAcuerdodeRazonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cierre")
    private Integer idCierre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_expediente", nullable = false)
    private ExpedienteEntity expediente;

    @Column(name = "medio_notificacion", nullable = false)
    private String medioNotificacion;

    @Column(name = "ruta_archivo_acuerdo", nullable = false)
    private String rutaArchivoAcuerdo;

    @Column(name = "fecha_cierre", nullable = false)
    private LocalDateTime fechaCierre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_cierre", nullable = false)
    private UsuarioEntity usuarioCierre;
}
