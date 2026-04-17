package com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "correspondencia") 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CorrespondenciaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_correspondencia")
    private Long id;

    @Column(name = "folio_unico", unique = true)
    private String folioUnico;

    @Column(name = "num_oficio_externo")
    private String numeroOficio;

    @Column(name = "dependencia_remitente")
    private String dependenciaRemitente;

    @Column(name = "nombre_remitente")
    private String nombreRemitente;

    @Column(name = "asunto")
    private String asunto;

    @Column(name = "id_estatus")
    private Long idEstatus;

    @Column(name = "id_usuario_captura")
    private Long idUsuarioCaptura;

    @Column(name = "observaciones_validacion")
    private String observaciones;
}
