package com.sigcqal.api.domain.Catalogo.Estatus.Model;

public class Estatus {
    private Integer idEstatus;
    private String nombreEstatus;
    private String descripcion;

    public Estatus() {
    }

    public Integer getIdEstatus() { return idEstatus; }
    public void setIdEstatus(Integer idEstatus) { this.idEstatus = idEstatus; }

    public String getNombreEstatus() { return nombreEstatus; }
    public void setNombreEstatus(String nombreEstatus) { this.nombreEstatus = nombreEstatus; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}