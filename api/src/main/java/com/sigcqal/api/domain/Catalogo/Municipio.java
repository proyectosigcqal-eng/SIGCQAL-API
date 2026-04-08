package com.sigcqal.api.domain.Catalogo;

public class Municipio {
    private Long id;
    private Integer idEstado;
    private String nombreMunicipio;
   
    public Municipio() {
    }

    public Long getId() { return id; }
    public String getNombreMunicipio() { return nombreMunicipio; }
    public Integer getIdEstado() { return idEstado; }

    public void setId(Long id) { this.id = id; }
    public void setNombreMunicipio(String nombreMunicipio) { this.nombreMunicipio = nombreMunicipio; }
    public void setIdEstado(Integer idEstado) { this.idEstado = idEstado; }
    
}
