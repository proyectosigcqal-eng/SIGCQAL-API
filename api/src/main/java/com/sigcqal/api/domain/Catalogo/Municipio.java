package com.sigcqal.api.domain.Catalogo;

public class Municipio {
   private Long id;
    private String nombreMunicipio;
    private Estado estado; 

    public Municipio() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombreMunicipio() { return nombreMunicipio; }
    public void setNombreMunicipio(String nombreMunicipio) { this.nombreMunicipio = nombreMunicipio; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    
}
