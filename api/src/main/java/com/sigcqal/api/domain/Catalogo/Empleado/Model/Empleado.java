package com.sigcqal.api.domain.Catalogo.Empleado.Model;

public class Empleado {
    private Long id;
    private String nombreCompleto;
    private String cargo;
    private Integer idArea;

    public Empleado() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public Integer getIdArea() { return idArea; }
    public void setIdArea(Integer idArea) { this.idArea = idArea; }
}
