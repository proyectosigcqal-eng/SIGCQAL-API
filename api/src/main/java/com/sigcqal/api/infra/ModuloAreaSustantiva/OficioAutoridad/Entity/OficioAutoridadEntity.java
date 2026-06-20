package com.sigcqal.api.infra.ModuloAreaSustantiva.OficioAutoridad.Entity;

import java.time.LocalDate;
import com.sigcqal.api.infra.ModuloAreaSustantiva.QuejasAri.Entity.QuejasAriEntity; // Ajusta según tu paquete
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "quejas_oficios_autoridad", schema = "sustantiva")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OficioAutoridadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_oficio_autoridad")
    private Integer idOficioAutoridad;

    // Relación con ARI
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ari", nullable = false)
    private QuejasAriEntity ari;

    @Column(name = "num_oficio_comisionado", length = 50, nullable = false, unique = true)
    private String numOficioComisionado;

    @Column(name = "fecha_envio_oficio", nullable = false)
    private LocalDate fechaEnvioOficio;

    @Column(name = "fecha_notificacion_quejoso")
    private LocalDate fechaNotificacionQuejoso;
}