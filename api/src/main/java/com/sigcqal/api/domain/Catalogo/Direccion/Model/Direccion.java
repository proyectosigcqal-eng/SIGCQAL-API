package com.sigcqal.api.domain.Catalogo.Direccion.Model;

public class Direccion {
    private Long id;
    private String calle;
    private String numExt;
    private String numInt;
    private String colonia;
    private String cp;
    private Integer idMunicipio;
    private Integer idEstado;

    public Direccion() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }

    public String getNumExt() { return numExt; }
    public void setNumExt(String numExt) { this.numExt = numExt; }

    public String getNumInt() { return numInt; }
    public void setNumInt(String numInt) { this.numInt = numInt; }

    public String getColonia() { return colonia; }
    public void setColonia(String colonia) { this.colonia = colonia; }

    public String getCp() { return cp; }
    public void setCp(String cp) { this.cp = cp; }

    public Integer getIdMunicipio() { return idMunicipio; }
    public void setIdMunicipio(Integer idMunicipio) { this.idMunicipio = idMunicipio; }

    public Integer getIdEstado() { return idEstado; }
    public void setIdEstado(Integer idEstado) { this.idEstado = idEstado; }
}
