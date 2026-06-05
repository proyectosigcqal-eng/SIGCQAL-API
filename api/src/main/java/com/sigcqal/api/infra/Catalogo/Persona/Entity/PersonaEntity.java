package com.sigcqal.api.infra.Catalogo.Persona.Entity;

import com.sigcqal.api.infra.Catalogo.Direccion.Entity.DireccionEntity;
import com.sigcqal.api.infra.Catalogo.TipoPersona.Entity.TipoPersonaEntity;

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
@Table(name = "personas", schema = "catalogos")
@Data
public class PersonaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_direccion")
    private DireccionEntity direccion;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido_paterno")
    private String apellidoPaterno;

    @Column(name = "apellido_materno")
    private String apellidoMaterno;

    @Column(name = "curp")
    private String curp;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "comunidad")
    private String comunidad;

    @Column(name = "rfc")
    private String rfc;

    @Column(name = "rec")
    private String rec;

    @Column(name = "identificacion_oficial")
    private String identificacionOficial;

    @Column(name = "telefono_fijo")
    private String telefonoFijo;

    @Column(name = "numero_id_folio")
    private String numeroIdFolio;

    @Column(name = "correo")
    private String correo;

    @Column(name = "tipo_identificacion")
    private String tipoIdentificacion;

    @ManyToOne
    @JoinColumn(name = "id_tipo_persona")
    private TipoPersonaEntity tipoPersona;
}
