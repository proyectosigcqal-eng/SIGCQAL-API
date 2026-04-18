package com.sigcqal.api.infra.ModuloCorrespondencia.Memorandum.Entity;

import java.time.LocalDateTime;

import com.sigcqal.api.infra.Catalogo.Area.Entity.AreaEntity;
import com.sigcqal.api.infra.Catalogo.Usuario.Entity.UsuarioEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.Correspondencia.Entity.CorrespondenciaEntity;
import com.sigcqal.api.infra.ModuloCorrespondencia.PlantillaMemorandum.Entity.PlantillaMemorandumEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "memorandums") 
@Data 
@NoArgsConstructor
@AllArgsConstructor
public class MemorandumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_memo") 
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_correspondencia") 
    private CorrespondenciaEntity correspondencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_emisor") 
    private UsuarioEntity usuarioEmisor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_firmante")
    private UsuarioEntity usuarioFirmante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plantilla")
    private PlantillaMemorandumEntity plantilla;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area") 
    private AreaEntity area;

    @Column(name = "num_memo") 
    private String numMemo;

    @Column(name = "instruccion_seguimiento")
    private String instruccionSeguimiento;

    @Column(name = "fecha_emision")
    private LocalDateTime fechaEmision;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "url_memorandum_generado")
    private String urlMemorandumGenerado;

    @Column(name = "folio_unico")
    private String folioUnico;

    @PrePersist
    protected void onCreate() {
        if (this.fechaEmision == null) {
            this.fechaEmision = LocalDateTime.now();
        }
    }
}