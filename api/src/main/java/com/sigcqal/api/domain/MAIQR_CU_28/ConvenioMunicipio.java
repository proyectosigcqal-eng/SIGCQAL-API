package com.sigcqal.api.domain.MAIQR_CU_28;


public class ConvenioMunicipio {
    private Long id;
    private String nombreMunicipio;
    private boolean vigente;

   
    public ConvenioMunicipio() {
    }


    public Long getId() { return id; }
    public String getNombreMunicipio() { return nombreMunicipio; }
    public boolean isVigente() { return vigente; }

    public void setId(Long id) { this.id = id; }
    public void setNombreMunicipio(String nombreMunicipio) { this.nombreMunicipio = nombreMunicipio; }
    public void setVigente(boolean vigente) { this.vigente = vigente; }
   
    public boolean estaVigente() {
        return vigente;
    }
}