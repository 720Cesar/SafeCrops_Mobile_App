package com.example.safecrops;

public class Cultivos {

    String id, nombreCultivos, descripcionCultivos;

    public Cultivos(){

    }

    public Cultivos(String id, String nombreCultivos, String descripcionCultivos) {
        this.id = id;
        this.nombreCultivos = nombreCultivos;
        this.descripcionCultivos = descripcionCultivos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreCultivos() {
        return nombreCultivos;
    }

    public void setNombreCultivos(String nombreCultivos) {
        this.nombreCultivos = nombreCultivos;
    }

    public String getDescripcionCultivos() {
        return descripcionCultivos;
    }

    public void setDescripcionCultivos(String descripcionCultivos) {
        this.descripcionCultivos = descripcionCultivos;
    }
}
